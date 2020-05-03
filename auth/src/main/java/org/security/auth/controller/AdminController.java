package org.security.auth.controller;

import org.security.auth.entity.User;
import org.security.auth.service.UserService;
import org.security.common.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/enable")
    public HttpResult userEnableByAdmin(@RequestBody User user){
        return userService.enableUser(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/users")
    public HttpResult selectUsers(@RequestBody User user ){
        return  userService.userList(user);
    }
}
