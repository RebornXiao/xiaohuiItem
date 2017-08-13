package com.xlibao.saas.market.core.message;

import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.saas.market.core.message.client.MarketMessageEventListenerImpl;
import com.xlibao.saas.market.core.message.hardware.HexMessageEventListenerImpl;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class MessageEventFactory {

    private MessageEventListener marketMessageEventListener = new MarketMessageEventListenerImpl();

    private MessageEventListener hexMessageEventListener = new HexMessageEventListenerImpl();

    private static final MessageEventFactory instance = new MessageEventFactory();

    private MessageEventFactory() {
    }

    public static MessageEventFactory getInstance() {
        return instance;
    }

    public MessageEventListener getMarketMessageEventListener() {
        return marketMessageEventListener;
    }

    public MessageEventListener getHexMessageEventListener() {
        return hexMessageEventListener;
    }
}