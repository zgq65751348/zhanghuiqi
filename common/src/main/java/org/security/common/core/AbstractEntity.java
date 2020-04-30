package org.security.common.core;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @see MyBatis Plus实体类新增更新操作
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class AbstractEntity<U extends AbstractEntity> extends Model  implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */

    @TableId
    private String id;


    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    public Date updateTime;

    /**
     * 删除标记 0:未删除 1:已删除
     */
    @TableField(value = "del_fag", fill = FieldFill.INSERT)
    @TableLogic
    public Integer delFag;

    protected Serializable pkVal() {
        return null;
    }


}
