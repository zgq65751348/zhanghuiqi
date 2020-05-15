package org.security.auth.hibernate;

import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.security.common.core.BaseEntity;

/**
 * @see 用户类
 * @author Administrator
 * 雅诗兰黛  熬夜不怕黑眼圈
 */

@Table(name = "user")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User extends BaseEntity {
	
	@Column(name = "username",nullable = false,columnDefinition="varchar(50) COMMENT '用户名'")
	private String username;
	
	@Column(name = "password",nullable = false,columnDefinition="varchar(50) COMMENT '密码'")
	private String password;

	@Column(name = "last_login_time",columnDefinition=" datetime COMMENT '最后登录时间'")
	private Date lastLoginTime;
	
	@Column(name = "enabled",nullable = false,columnDefinition=" bit(1)  DEFAULT b'0' COMMENT '是否被拉黑 1：是  0：否'")
	private Boolean enabled;
	
	@Column(name = "host",nullable = false,columnDefinition="varchar(50) COMMENT '登录主机'")
	private String host;

	@Column(name = "headPortrait",nullable = false,columnDefinition="varchar(700) COMMENT '用户头像'")
	private String headPortrait;

	@Column(name = "sex",nullable = false,columnDefinition="int(1) COMMENT '用户性别'")
	private int sex;

	@Column(name = "age",nullable = false,columnDefinition="int(3) COMMENT '用户年龄'")
	private int age;

	@Column(name = "enable",nullable = false,columnDefinition="int(1) COMMENT '操作用户'")
	private Integer enable;


	@ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();

}
