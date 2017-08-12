package com.xlibao.saas.market.core.message;

import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.saas.market.core.message.client.MessageEventListenerImpl;
import com.xlibao.saas.market.core.message.hardware.HexMessageEventListenerImpl;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class MessageEventFactory {

    private MessageEventListener messageEventListener = new MessageEventListenerImpl();

    private MessageEventListener hexMessageEventListener = new HexMessageEventListenerImpl();

    private static final MessageEventFactory instance = new MessageEventFactory();

    private MessageEventFactory() {
    }

    public static MessageEventFactory getInstance() {
        return instance;
    }

    public MessageEventListener getMessageEventListener() {
        return messageEventListener;
    }

    public MessageEventListener getHexMessageEventListener() {
        return hexMessageEventListener;
    }
}