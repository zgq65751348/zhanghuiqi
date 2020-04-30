package org.security.auth.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.security.auth.dto.AuthDto;
import org.security.auth.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    @Select(value = "select r.id,r.name from role r ,user_roles ur where r.id = ur.roles_id and ur.user_id = #{userId}")
    List<Role> findRolesByUserId(String  userId);

    List<Role> selectRoleByName(AuthDto authDto);

}
