package org.security.main.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;

/**
 * @see SpringDataJpa实体类新增更新操作
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity implements Serializable {
	
	   private static final long serialVersionUID = -3817814454956090470L;

		@Id
	 	@GeneratedValue(generator="snowflakeId")
		@GenericGenerator(name="snowflakeId", strategy="org.security.common.core.SnowflakeId" )
	    private String id;
		
	    @CreatedDate
	    @Column(name = "create_time",nullable = false,columnDefinition=" datetime COMMENT '添加时间'")
	    private Date createTime;
	    
	    @Column(name = "update_time",nullable = false,columnDefinition="datetime COMMENT '更新时间'")
	    @LastModifiedDate
	    private Date updateTime;
	    
	    @Column(name = "del_fag",nullable = false,columnDefinition="int(1)  DEFAULT '0' COMMENT '0:未删除 1:已删除'")
	    public Integer delFag;

}
