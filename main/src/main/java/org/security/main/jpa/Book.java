package org.security.main.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "book")
public class Book extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "category_id",columnDefinition="varchar(600) COMMENT '书籍所属分类'")
	private String categoryId;
	
	@Column(name = "book_name",columnDefinition="varchar(100) COMMENT '名称'")
	private String bookName;
	
	@Column(name = "is_share",columnDefinition="bit(1) DEFAULT 0  COMMENT '是不是共享书籍 0：出售 1：共享'")
	private Boolean isShare;
	
	@Column(name = "price",columnDefinition="decimal(9) COMMENT '出售价格'")
	private BigDecimal price;
	
	@Column(name = "original_price",columnDefinition="decimal(9) COMMENT '出售原价'")
	private BigDecimal originalPrice;
	
	@Column(name = "qq",columnDefinition="int(10) COMMENT '联系qq'")
	private Integer qq;
	
	@Column(name = "wechat",columnDefinition="varchar(100) COMMENT '联系微信'")
	private String wechat;
	
	@Column(name = "reading_times",columnDefinition="int(20) COMMENT '阅读次数'")
	private Integer readingTimes;
	
	@Column(name = "comments",columnDefinition="int(20) COMMENT '评论次数'")
	private Integer comments;
	
	@Column(name = "directions",columnDefinition="varchar(2000) COMMENT '说明'")
	private String directions;
	
	@Column(name = "stock",columnDefinition="int(20) COMMENT '库存数量'")
	private Integer stock;
	
	@Column(name = "screenshot",columnDefinition="varchar(600) COMMENT '图片地址'")
	private String screenshot;
	
	@Column(name = "user_id",columnDefinition="varchar(600) COMMENT '书籍所属者id'")
	private String userId;
	
	@Column(name = "is_examine",columnDefinition="bit(1) DEFAULT 0  COMMENT  '管理员审核 ：0：未通过 1：已通过'")
	private Boolean isExamine;
	
	@Column(name = "status",columnDefinition="int(1) COMMENT '当前状态 0：可借/可售  1：已借出 2：已售出'")
	private Integer status;

}
