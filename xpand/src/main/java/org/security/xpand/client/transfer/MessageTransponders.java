package org.security.xpand.client.transfer;

/**
 * @author chen.qian
 * @date 2018/3/23
 */
public class MessageTransponders {

    public static TransponderFactory defaultMessageTransponder() {
        return new DefaultTransponderFactory();
    }

}
