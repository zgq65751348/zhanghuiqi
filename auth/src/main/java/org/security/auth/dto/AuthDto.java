package org.security.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthDto {

    String userId;
    String roleId = "1b";
    String name ;

}
