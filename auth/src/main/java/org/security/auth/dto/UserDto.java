package org.security.auth.dto;

import lombok.Data;

@Data
public class UserDto {

	private  String userId;
	
	private String code;
	
	private String username;
	
	private String password;

}
