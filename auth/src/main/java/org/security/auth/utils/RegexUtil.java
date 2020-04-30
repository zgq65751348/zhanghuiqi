package org.security.auth.utils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @see 邮箱地址手机号码验证工具类
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */
public class RegexUtil {

	private static String code = "1234567890";
	 /**
     * 手机号正则,'1'+ 10个数字 匹配通过
     */
    private static final String REGEX_MOBILE = "^(1)[\\d]{10}$";

    /**
     * 邮箱正则,xxx@xx.xx即可
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    public static boolean checkMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean checkEMail(String eMail) {
        return Pattern.matches(REGEX_EMAIL, eMail);
    }
    
    public static String randomCode() {
    	StringBuilder sb=new StringBuilder(6);
    	for(int i=0;i<6;i++){
    	char ch=code.charAt(new Random().nextInt(code.length()));
    	sb.append(ch);
    	}
    	return sb.toString();
    }
    
}
