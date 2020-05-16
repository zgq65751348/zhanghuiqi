package org.security.xpand.annotation;

import org.security.xpand.config.CanalClientConfiguration;
import org.security.xpand.config.CanalConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the canal client
 *
 * @author chen.qian
 * @date 2018/3/19
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CanalConfig.class, CanalClientConfiguration.class})
public @interface EnableCanalClient {
}
