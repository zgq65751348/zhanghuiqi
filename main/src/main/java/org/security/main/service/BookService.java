package org.security.main.service;

import org.security.common.exception.HttpResult;
import org.security.main.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
public interface BookService extends IService<Book> {

    HttpResult releaseBookByUser(Book book);

    HttpResult examineBookByAdmin(Book book);

    HttpResult selectNotExamine(Book book);

    HttpResult selectExamine(Book book);

}
