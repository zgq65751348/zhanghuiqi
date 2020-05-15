package org.security.main.service.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.security.common.core.SnowflakeId;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.security.main.config.RsaKeyProperties;
import org.security.main.core.MainStatus;
import org.security.main.entity.Book;
import org.security.main.entity.Category;
import org.security.main.filter.JwtVerifyFilter;
import org.security.main.repository.BookMapper;
import org.security.main.repository.CategoryMapper;
import org.security.main.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@Service
public class BookServiceApi extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    private BookMapper bookMapper;

    /**
     * 用户发布图书
     *
     * @param book
     * @return
     */
    @Override
    public HttpResult releaseBookByUser(Book book) {
        Category category = categoryMapper.selectById(book.getCategoryId());
        if (Objects.isNull(category)) {
            throw new ExceptionHandlerClass(Code.NOT_FOUND_CATEGORY);
        }
        JwtVerifyFilter jwtVerifyFilter = new JwtVerifyFilter(authenticationManager, rsaKeyProperties);
        String userId = jwtVerifyFilter.getUserByAuthentication(httpServletRequest).getId();
        String id = SnowflakeId.getId();
        book.setUserId(userId);
        book.setId(id);
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        book.setDelFag(1);
        bookMapper.insert(book);
        return HttpResult.success();
    }

    /**
     * 管理员审核书籍
     *
     * @param book
     * @return
     */
    @Override
    public HttpResult examineBookByAdmin(Book book) {
        Book examineBook = bookMapper.selectById(book.getId());
        if (Objects.isNull(examineBook)) {
            throw new ExceptionHandlerClass(Code.NO_DATA);
        }
        if (examineBook.getIsExamine() == MainStatus.Loaned) {
            throw new ExceptionHandlerClass(Code.privilege_grant_failed);
        }
        examineBook.setStatus(MainStatus.Loanable);
        examineBook.setIsExamine(book.getIsExamine());
        examineBook.setUpdateTime(new Date());
        if (StringUtils.isNotEmpty(book.getRemarks())) {
            examineBook.setRemarks(book.getRemarks());
        }
        bookMapper.updateById(examineBook);
        return HttpResult.success();
    }

    /**
     * 管理员查看待审核或未审核未通过的书籍
     *
     * @param book
     * @return
     */
    @Override
    public HttpResult selectNotExamine(Book book) {
        IPage<Book> bookIPage = bookMapper.selectPage(new Page<Book>(book.getPage(), book.getSize()),
                new QueryWrapper<Book>().orderByDesc(true,"reading_times").lambda()
                        .eq(Book::getIsExamine, book.getIsExamine())
                        .like(!StringUtils.isEmpty(book.getBookName()), Book::getBookName, book.getBookName())
                        .eq((book.getCategoryId() != null), Book::getCategoryId, book.getCategoryId())
        );
        return HttpResult.success(bookIPage);
    }

    /**
     * 查看审核通过的书籍
     *
     * @param book
     * @return
     */
    @Override
    public HttpResult selectExamine(Book book) {
        IPage<Book> bookIPage = bookMapper.selectPage(new Page<Book>(book.getPage(), book.getSize()),
                new QueryWrapper<Book>() .orderByDesc(true,"reading_times").lambda()
                        .eq(Book::getIsExamine, MainStatus.Loaned)
                        .eq((null != book.getIsShare()),Book::getIsShare,book.getIsShare())
                        .eq((null != book.getStatus()),Book::getStatus,book.getStatus())
                        .like(!StringUtils.isEmpty(book.getBookName()), Book::getBookName, book.getBookName())
                        .eq((book.getCategoryId() != null), Book::getCategoryId, book.getCategoryId())
        );
        return HttpResult.success(bookIPage);
    }


}
