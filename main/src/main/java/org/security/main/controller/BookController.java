package org.security.main.controller;


import org.security.common.exception.HttpResult;
import org.security.main.entity.Book;
import org.security.main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = "/release")
    public HttpResult  releaseBookByUser(@RequestBody Book book){
        return bookService.releaseBookByUser(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/examine")
    public HttpResult examineBookByAdmin(@RequestBody Book book){
        return bookService.examineBookByAdmin(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/not/Examine")
    public HttpResult selectNotExamine(@RequestBody Book book){
        return bookService.selectNotExamine(book);
    }



}
