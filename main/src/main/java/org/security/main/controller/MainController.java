package org.security.main.controller;

import org.security.common.exception.HttpResult;
import org.security.main.entity.Category;
import org.security.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/main")
public class MainController {



    @GetMapping(value = "/index")
    public HttpResult index(){
        return HttpResult.success("success index!");
    }


}
