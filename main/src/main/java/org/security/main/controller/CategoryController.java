package org.security.main.controller;


import org.security.common.exception.HttpResult;
import org.security.main.entity.Category;
import org.security.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *  管理员添加分类吧
     * @param category
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/create")
    public HttpResult create(@RequestBody Category category) {
        return categoryService.createCategoryByAdmin(category);
    }


}
