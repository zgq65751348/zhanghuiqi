package org.security.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @serial  异常信息响应客户端
 * 雅诗兰黛  熬夜不怕黑眼圈
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ExceptionHandlerClass extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
	public ExceptionHandlerClass(Code co) {
		super(co.getMsg());
		this.code=co.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
