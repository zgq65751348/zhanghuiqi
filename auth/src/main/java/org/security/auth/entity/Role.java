package org.security.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.security.common.core.AbstractEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "role")
public class Role extends AbstractEntity<Role> implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名称*
     */
    private String name;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
