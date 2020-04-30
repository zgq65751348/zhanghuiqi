package org.security.main.core;

import lombok.Data;

@Data
public class MainStatus {
	
	//可借出
	public static final Integer Loanable = 0;
	
	//已借出 Loaned
	public static final Integer Loaned = 1;
	
	//已出售
	public static final Integer Sold = 2;
	
	
	
	
}
