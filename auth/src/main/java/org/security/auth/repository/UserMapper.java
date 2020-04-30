package org.security.auth.repository;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.security.auth.dto.AuthDto;
import org.security.auth.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
public interface UserMapper extends BaseMapper<User> {

    @Select(value = "select * from user where username = #{username}")
    @Results(value = {
            @Result(id= true,property = "id",column = "id"),
            @Result(property ="roles",column = "id",javaType = List.class,many = @Many(select = "org.security.auth.repository.RoleMapper.findRolesByUserId"))
    })
    User findUserByName(String username);

    void newUserAuth(AuthDto authDto);

    void createNewUser(User user);

    void modifyPassword(User user);

}
