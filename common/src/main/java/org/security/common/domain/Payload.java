package org.security.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author  全球最帅的程序员
 * @serial  载荷对象
 */
@Data
public class Payload<T> {

    private String id;

    private T userInfo;

    private Date expiration;

}
