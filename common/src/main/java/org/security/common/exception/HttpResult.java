package org.security.common.exception;

import lombok.Data;

/**
 * @see 客户端请求数据Json响应
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */
@Data
public class HttpResult {
	
	private int code = 200;
    private String msg;
    private Object data;


 

 
    /**
     * 请求成功时的响应结果
     * @return HttpResult
     */
    public static HttpResult success(){
    	HttpResult httpResult = new HttpResult();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg("SUCCESS");
        return httpResult;
    }

 
    /**
     * 请求成功时的响应结果
     * @param msg 自定义响应内容
     * @return HttpResult
     */
    public static HttpResult success(String msg){
    	HttpResult httpResult = new HttpResult();
        httpResult.setMsg(msg);
        httpResult.setCode(httpResult.code);
        return httpResult;
    }
 
    /**
     * 请求成功时的响应结果
     * @param data 要响应的数据
     * @return HttpResult
     */
    public static HttpResult success(Object data){
    	HttpResult httpResult = new HttpResult();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg("SUCCESS");
        httpResult.setData(data);
        return httpResult;
    }
 
    /**
     * 请求成功时的响应结果
     * @param msg 自定义响应的内容
     * @param data 自定义响应数据
     * @return HttpResult
     */
    public static HttpResult success(String msg, Object data){
    	HttpResult httpResult = new HttpResult();
        httpResult.setCode(httpResult.code);
        httpResult.setMsg(msg);
        httpResult.setData(data);
        return httpResult;
    }
 
    /**
     * 请求失败时的响应内容
     * @return HttpResult
     */
    public static HttpResult failed(Code code){
    	HttpResult httpResult = new HttpResult();
        httpResult.setCode(code.getCode());
        httpResult.setMsg(code.getMsg());
        return httpResult;
    }
 
    /**
     * 请求失败时的相应内容
     * @param msg 自定义相应内容
     * @return HttpResult
     */
    public static HttpResult failed(Code code,String msg){
    	HttpResult httpResult = new HttpResult();
        httpResult.setCode(code.getCode());
        httpResult.setMsg(code.getMsg());
        return httpResult;
    }

    public static HttpResult failed(int code,String msg){
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(code);
        httpResult.setMsg(msg);
        return httpResult;
    }

}
