package org.security.main.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.security.common.core.AbstractEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "book")
public class Book extends AbstractEntity<Book> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String bookName;

    /**
     * 书籍所属分类
     */
    private String categoryId;

    /**
     * 评论次数
     */
    private Integer comments;

    /**
     * 说明
     */
    private String directions;

    /**
     * 管理员审核 ：0：未通过 1：已通过
     */
    private Boolean isExamine;

    /**
     * 是不是共享书籍 0：出售 1：共享
     */
    private Boolean isShare;

    /**
     * 出售原价
     */
    private BigDecimal originalPrice;

    /**
     * 出售价格
     */
    private BigDecimal price;

    /**
     * 联系qq
     */
    private Integer qq;

    /**
     * 阅读次数
     */
    private Integer readingTimes;

    /**
     * 图片地址
     */
    private String screenshot;

    /**
     * 当前状态 0：可借/可售  1：已借出 2：已售出
     */
    private Integer status;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 书籍所属者id
     */
    private String userId;

    /**
     * 联系微信
     */
    private String wechat;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
