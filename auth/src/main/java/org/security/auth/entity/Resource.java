package org.security.auth.entity;

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
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Resource extends AbstractEntity<Resource> {

    private static final long serialVersionUID = 1L;

    /**
     * 资源对应的方法名*
     */
    private String methodName;

    /**
     * 资源所对应的包路径*
     */
    private String methodPath;

    /**
     * 备注*
     */
    private String remark;

    /**
     * 资源ID*
     */
    private String resourceId;

    /**
     * 资源名称*
     */
    private String resourceName;

    /**
     * 控制的url*
     */
    private String resourceString;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
