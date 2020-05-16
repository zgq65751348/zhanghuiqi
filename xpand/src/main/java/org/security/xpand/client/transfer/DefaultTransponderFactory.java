package org.security.xpand.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import org.security.xpand.client.ListenerPoint;
import org.security.xpand.config.CanalConfig;
import org.security.xpand.event.CanalEventListener;

import java.util.List;
import java.util.Map;

/**
 * @author chen.qian
 * @date 2018/3/23
 */
public class DefaultTransponderFactory implements TransponderFactory {
    @Override
    public MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalEventListener> listeners,
                                             List<ListenerPoint> annoListeners) {
        return new DefaultMessageTransponder(connector, config, listeners, annoListeners);
    }
}
