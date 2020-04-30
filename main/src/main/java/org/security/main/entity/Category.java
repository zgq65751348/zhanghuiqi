package org.security.main.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName(value = "category")
public class Category extends AbstractEntity<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 类目图片地址
     */
    private String categoryPath;

    /**
     * 类目名称
     */
    private String name;

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer size;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
