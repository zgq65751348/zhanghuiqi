package org.security.common.exception;

import java.sql.SQLException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;

import com.sun.mail.smtp.SMTPAddressFailedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * 雅诗兰黛  熬夜不怕黑眼圈
 * @see全局异常处理类
 */

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    public HttpResult exceptionHandler(Exception e, HttpServletRequest request) {
        HttpResult httpResult = new HttpResult();

        if (e instanceof ExceptionHandlerClass) {
            log.error("异常状态码：{},异常信息提示：{},请求路径：{}", ((ExceptionHandlerClass) e).getCode(), e.getMessage(), request.getRequestURI());
            httpResult.setCode(((ExceptionHandlerClass) e).getCode());
            httpResult.setMsg(((ExceptionHandlerClass) e).getMessage());

        } else if (e instanceof SQLException) {
            httpResult.setCode(Code.SQL_ERROR.getCode());
            httpResult.setMsg(Code.SQL_ERROR.getMsg());
        } else if (e instanceof AccessDeniedException) {
            httpResult.setMsg(Code.NO_ROLE.getMsg());
            httpResult.setCode(Code.NO_ROLE.getCode());
        }else  if(e instanceof SMTPAddressFailedException){
            httpResult.setMsg(Code.MAIL_ERROR.getMsg());
            httpResult.setCode(Code.MAIL_ERROR.getCode());
        } else {
            httpResult.setCode(Code.SYS_ERROR.getCode());
            httpResult.setMsg(Code.SYS_ERROR.getMsg());
        }
        return httpResult;
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public HttpResult security(Exception e, HttpServletRequest request){
        return HttpResult.failed(Code.TOKEN_EXPIRED);
    }

}
