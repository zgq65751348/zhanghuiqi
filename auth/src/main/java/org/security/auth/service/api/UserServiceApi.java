package org.security.auth.service.api;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.security.auth.config.RsaKeyProperties;
import org.security.auth.dto.AuthDto;
import org.security.auth.dto.UserDto;
import org.security.auth.entity.Role;
import org.security.auth.entity.User;
import org.security.auth.filter.JwtVerifyFilter;
import org.security.auth.repository.RoleMapper;
import org.security.auth.repository.UserMapper;
import org.security.auth.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.security.auth.utils.HostUtil;
import org.security.auth.utils.MailUtil;
import org.security.auth.utils.RegexUtil;
import org.security.common.core.SnowflakeId;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
@Service
public class UserServiceApi extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired(required = false)
    private JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByName(username);
        if (!Objects.isNull(user)) {
            String ip = HostUtil.getClientIpAddress(request);
            System.out.println("---- ip address :" + ip);
            if (HostUtil.checkIp(ip)) {
                ip = "";
            }
         /*   if(BooleanUtils.isFalse(user.isEnabled())){
                 throw new ExceptionHandlerClass(Code.USER_IS_DISABLED);
            }*/
            user.setHost(ip);
            user.setLastLoginTime(new Date());
            userMapper.update(user, new QueryWrapper<User>().eq("id", user.getId()));
        }
        return user;
    }

    @Override
    public HttpResult register(UserDto userDto) {
        String mail = userDto.getUsername();
        if (mail.isEmpty() || !RegexUtil.checkEMail(mail)) {
            throw new ExceptionHandlerClass(Code.MAIL_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", mail);
        User u = userMapper.selectOne(queryWrapper);
        if (!Objects.isNull(u)) {
            throw new ExceptionHandlerClass(Code.MAIL_IS_REGISTER);
        }
        String code = redisTemplate.opsForValue().get(mail);
        if (!userDto.getCode().equals(code)) {
            throw new ExceptionHandlerClass(Code.CODE_ERROR);
        }
        String password = bCryptPasswordEncoder.encode(userDto.getPassword());
        User user = new User();
        String userId = SnowflakeId.getId();
        user.setId(userId);
        user.setUsername(mail);
        user.setPassword(password);
        AuthDto dto = new AuthDto();
        dto.setName("USER");
        List<Role> roles = roleMapper.selectRoleByName(dto);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setEnabled(true);
        user.setDelFag(0);
        System.out.println("创建时间：" + user.getCreateTime());
        userMapper.createNewUser(user);
        for (Role role : roles) {
            System.out.println("数据库里的权限为：" + role.getName());
            AuthDto authDto = new AuthDto();
            authDto.setRoleId(role.getId());
            authDto.setUserId(userId);
            userMapper.newUserAuth(authDto);
        }
        return HttpResult.success();
    }

    @Override
    public HttpResult sendCode(UserDto userDto) {
        String mail = userDto.getUsername();
        if (mail.isEmpty() || !RegexUtil.checkEMail(mail)) {
            throw new ExceptionHandlerClass(Code.MAIL_ERROR);
        }
        String code = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        javaMailSenderImpl.send(MailUtil.sendCode(mail, code));
        redisTemplate.opsForValue().set(mail, code, 5, TimeUnit.MINUTES);
        return HttpResult.success("验证码已经发送，请注意查收！");
    }

    @Override
    public HttpResult modifyPassword(UserDto userDto) {
        String mail = userDto.getUsername();
        if (mail.isEmpty() || !RegexUtil.checkEMail(mail)) {
            throw new ExceptionHandlerClass(Code.MAIL_ERROR);
        }
        User u = new User();
        u.setUsername(mail);
        User userInDb = userMapper.findUserByName(u.getUsername());
        if (Objects.isNull(userInDb)) {
            throw new ExceptionHandlerClass(Code.USER_NOT_FOUND);
        }
        String code = redisTemplate.opsForValue().get(mail);
        if (!userDto.getCode().equals(code)) {
            throw new ExceptionHandlerClass(Code.CODE_ERROR);
        }
        String password = bCryptPasswordEncoder.encode(userDto.getPassword());
        userInDb.setPassword(password);
        userMapper.modifyPassword(userInDb);
        return HttpResult.success("密码修改成功");
    }

    @Override
    public HttpResult modifyUserInfo(User user) {
        JwtVerifyFilter jwtVerifyFilter = new JwtVerifyFilter(authenticationManager, rsaKeyProperties);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtVerifyFilter.getAuthentication(request);
        User me = (User) usernamePasswordAuthenticationToken.getPrincipal();
        if (Objects.isNull(me)) {
            throw new ExceptionHandlerClass(Code.privilege_grant_failed);
        }
        user.setId(me.getId());
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return HttpResult.success();
    }

    @Override
    public HttpResult getUserInfo() {
        JwtVerifyFilter jwtVerifyFilter = new JwtVerifyFilter(authenticationManager, rsaKeyProperties);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtVerifyFilter.getAuthentication(request);
        User me = (User) usernamePasswordAuthenticationToken.getPrincipal();
        if (Objects.isNull(me)) {
            throw new ExceptionHandlerClass(Code.privilege_grant_failed);
        }
        User user = userMapper.selectById(me.getId());
        if (Objects.isNull(user)) {
            throw new ExceptionHandlerClass(Code.USER_NOT_FOUND);
        }
        return HttpResult.success(user);
    }

    /**
     * 管理员拉黑用户
     *
     * @param user
     * @return
     */
    @Override
    public HttpResult enableUser(User user) {
        User u = userMapper.selectById(user.getId());
        if (Objects.isNull(u)) {
            throw new ExceptionHandlerClass(Code.USER_NOT_FOUND);
        }
        List<Role> roles = roleMapper.rolesOfUser(user.getId());
        for (Role role : roles
        ) {
            if (role.getName().equals("ADMIN") || role.getName().equals("ROOT")) {
                throw new ExceptionHandlerClass(Code.NO_ROLE);
            }
        }
        u.setEnabled(false);
        u.setUpdateTime(new Date());
        userMapper.updateById(u);
        return HttpResult.success();
    }

    /**
     * 查看用户列表
     *
     * @param user
     * @return
     */
    @Override
    public HttpResult userList(User user) {
        Integer enable = user.getEnable();
        Boolean enabled = false;
        if( null != enable ){
            System.out.println("user.getEnable:"+user.getEnable());
            enabled = user.getEnable() == 1 ? true:false;
            System.out.println("enabled  "+enabled);
        }
        IPage<User> iPage = userMapper.selectPage(new Page<User>(user.getPage(), user.getSize()),
                new QueryWrapper<User>().orderByDesc(true, "id").lambda()
                        .like(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                        .eq(StringUtils.isNotEmpty(user.getId()), User::getId, user.getId())
                        .eq(!Objects.isNull(enable),User::isEnabled, enabled)
        );
        return HttpResult.success(iPage);
    }
}
