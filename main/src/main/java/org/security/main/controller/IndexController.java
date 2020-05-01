package org.security.main.controller;

import org.security.common.exception.HttpResult;
import org.security.main.entity.Book;
import org.security.main.entity.Category;
import org.security.main.service.BookService;
import org.security.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/1")
    public HttpResult index(){
        return HttpResult.success("index");
    }

    @PostMapping(value = "/category/query")
    public HttpResult selectAll(@RequestBody Category category){
        return categoryService.selectCategory(category);
    }

    @PostMapping(value = "/book/isExamine")
    public HttpResult selectExamine(@RequestBody Book book){
        return bookService.selectExamine(book);
    }
}
