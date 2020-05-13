package org.security.auth.controller;

import io.jsonwebtoken.lang.Strings;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.security.auth.dto.FastDFSFile;
import org.security.auth.dto.UserDto;
import org.security.auth.service.UserService;
import org.security.auth.utils.FastDFSClient;
import org.security.common.exception.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/index")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/upload")
    public HttpResult upload(@RequestParam(value = "file")MultipartFile file) throws IOException, MyException {
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),file.getBytes(),
                Strings.getFilenameExtension(file.getOriginalFilename()));  //这行代码有点问题
        String [] uploads = FastDFSClient.upload(fastDFSFile);
        String url = FastDFSClient.getTracker()+"/"+uploads[0]+"/"+uploads[1];
        return HttpResult.success(url);
    }

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
