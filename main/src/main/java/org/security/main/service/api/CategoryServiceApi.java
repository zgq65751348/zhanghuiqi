package org.security.main.service.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.security.common.core.SnowflakeId;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.security.main.entity.Category;
import org.security.main.repository.CategoryMapper;
import org.security.main.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-28
 */
@Service
public class CategoryServiceApi extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *  管理员添加书籍分类类目
     * @param category
     * @return
     */
    @Override
    public HttpResult createCategoryByAdmin(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "name",category.getName());
        Category c = categoryMapper.selectOne(queryWrapper) ;
        if(!Objects.isNull(c)) {
            throw new ExceptionHandlerClass(Code.DUPLICAT_DATA);
        }
        String id = SnowflakeId.getId();
        category.setId(id);
        category.setDelFag(0);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryMapper.insert(category);
        return HttpResult.success();
    }

    /**
     *  查看分类
     * @param Category
     * @return
     */
    @Override
    public HttpResult selectCategory(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(category.getName())){
            queryWrapper.like("name",category.getName());
        }else {
            queryWrapper=null;
        }
        IPage<Category> categoryIPage = categoryMapper.selectPage(new Page<Category>(category.getPage(),category.getSize()),queryWrapper);
        return HttpResult.success(categoryIPage);
    }
}
