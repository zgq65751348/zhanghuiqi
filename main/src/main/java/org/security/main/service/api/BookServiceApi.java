package org.security.main.service.api;

import org.security.main.entity.Book;
import org.security.main.repository.BookMapper;
import org.security.main.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@Service
public class BookServiceApi extends ServiceImpl<BookMapper, Book> implements BookService {

}
