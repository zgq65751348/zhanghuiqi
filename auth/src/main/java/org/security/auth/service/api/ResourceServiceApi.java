package org.security.auth.service.api;

import org.security.auth.entity.Resource;
import org.security.auth.service.ResourceService;
import org.security.auth.repository.ResourceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 雅诗兰黛，熬夜不怕黑眼圈！
 * @since 2020-04-09
 */
@Service
public class ResourceServiceApi extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
