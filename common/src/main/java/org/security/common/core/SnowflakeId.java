package org.security.common.core;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @see 雪花算法Id生成器
 * @author Administrator
 *	雅诗兰黛  熬夜不怕黑眼圈
 */

public class SnowflakeId implements IdentifierGenerator{

    public SnowflakeId() {};

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return getId();
    }

    /**
     * 	该方法需要是线程安全的
     * @return String
     */
    public static String getId() {
        synchronized (SnowflakeId.class) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }
}
