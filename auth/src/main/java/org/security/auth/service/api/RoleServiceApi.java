package org.security.auth.service.api;

import org.security.auth.entity.Role;
import org.security.auth.repository.RoleMapper;
import org.security.auth.service.RoleService;
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
public class RoleServiceApi extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
