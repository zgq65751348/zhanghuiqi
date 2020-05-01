package org.security.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @see 客户端请求后台报错响应枚举
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Code {

	OK(200, "成功"),
    VALUE_NULL(300, "值为空"),
    PARAM_NULL(301, "请求参数处理异常"),
    SIGN_ERROR(400, "密码错误"),
    NO_LOGIN(401, "未登录"),
    NO_DATA(1001,"未找到相关数据"),
    SQL_ERROR(1007,"执行SQL脚本异常"),
    USER_NOT_FOUND(1008,"用户不存在！"),
    PASSWORD_ERROR(1001,"密码错误~"),
    MAIL_ERROR(550,"邮箱格式不正确"),
    MAIL_IS_REGISTER(1003,"邮箱已被注册"),
    CODE_ERROR(1004,"验证码错误"),
    NO_ROLE(403,"当前用户没有权限操作！！！"),
    privilege_grant_failed(402,"授权失败"),
    FILE_UPLOAD_ERROR(409,"文件上传失败"),
    FILE_NOT_FOUND(408,"文件不存在"),
    FILE_TOO_LARGE(530,"文件过大"),
    SERVER_BUSY(550,"服务器忙"),
    TOKEN_EXPIRED(600,"授权失效"),
    DUPLICAT_DATA(601,"数据重复"),
    NOT_FOUND_CATEGORY(602,"未找到相关类目"),
    SYS_ERROR(500, "请检查请求数据是否合法");

    private int code;
    private String msg;

}
