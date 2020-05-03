package org.security.auth.service;

import org.security.auth.dto.UserDto;
import org.security.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.security.common.exception.HttpResult;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
public interface UserService extends IService<User>, UserDetailsService {

    HttpResult register(UserDto userDto);

    HttpResult sendCode(UserDto userDto);

    HttpResult modifyPassword(UserDto userDto);

    HttpResult modifyUserInfo(User user);

    HttpResult getUserInfo();

    HttpResult enableUser(User user);

    HttpResult userList(User user);
}
