package org.security.auth.utils;


import org.springframework.mail.SimpleMailMessage;


/**
 * @see 邮箱验证码发送
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */
public class MailUtil {
	
	public static SimpleMailMessage sendCode(String mail,String code) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setSubject("张慧琦发的验证码");
		simpleMessage.setText("尊敬的用户本次验证码为"+code+";有效期为五分钟， 请妥善保存，如非本人操作请忽略此封邮件,谢谢您的支持！");
		simpleMessage.setTo(mail);
		simpleMessage.setFrom("65751348@qq.com");
		return simpleMessage;
	}

}
