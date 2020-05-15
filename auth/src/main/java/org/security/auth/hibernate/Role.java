package org.security.auth.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.security.common.core.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role")
public class Role  extends BaseEntity {

    @Column(name = "name",nullable = false,columnDefinition="varchar(100) COMMENT '权限名称*'")
    private String name;

}
