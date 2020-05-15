package org.security.main.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "category")
public class Category extends BaseEntity{

    @Column(name = "name",columnDefinition="varchar(50) COMMENT '类目名称'")
    private String name;

    @Column(name = "category_path",columnDefinition="varchar(500) COMMENT '类目图片地址'")
    private String categoryPath;


}
