package org.security.auth.controller;


import org.security.auth.entity.User;
import org.security.auth.service.UserService;
import org.security.common.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/auth")
    public HttpResult auth(){
        return HttpResult.success("授权成功！");
    }

    @PostMapping(value = "/modify/info")
    public HttpResult modifyUserInfo(@RequestBody User user){
        return userService.modifyUserInfo(user);
    }

    @GetMapping(value = "/info")
    public HttpResult getUserInfo(){
        return userService.getUserInfo();
    }

}
