package org.security.auth.controller;

import org.security.auth.dto.UserDto;
import org.security.auth.service.UserService;
import org.security.common.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/index")
public class AuthController {

    @Autowired
    private UserService userService;

    @PutMapping(value = "/register")
    public HttpResult register(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }

    @PostMapping(value = "/send")
    public HttpResult sendCode(@RequestBody UserDto userDto) {
        return userService.sendCode(userDto);
    }

    @PutMapping(value = "/modify/password")
    public HttpResult modifyPassword(@RequestBody UserDto userDto){
        return userService.modifyPassword(userDto);
    }


}
