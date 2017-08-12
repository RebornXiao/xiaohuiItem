package com.xlibao.saas.market.core.service;

import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.ShopProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/9.
 */
public class MessageHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerAdapter.class);

    private static final MessageHandlerAdapter instance = new MessageHandlerAdapter();

    private MessageHandlerAdapter() {}

    public static MessageHandlerAdapter getInstance() {
        return instance;
    }

    public void hardwareMessageExecute(NettySession session, String content) {
        String msgType = content.substring(4, 8);
        logger.info("Message type is " + msgType);
    }

    public void platformMessageExecute(NettySession session, MessageInputStream message) {
        short msgId = message.getMsgId();

        switch (msgId) {
            case MessageFactory.MSG_ID_HEARTBEAT: // 心跳 回复当前服务器时间
                break;
        }
    }

    public void shopMessageExecute(NettySession session, MessageInputStream message) {
        short msgId = message.getMsgId();

        logger.info("来自硬件的消息，消息ID：" + msgId + "；" + session.netTrack());
        switch (msgId) {
            case ShopProtocol.CS_HARDWARE:
                break;
        }
    }

    public void logicMessageExecute(NettySession session, MessageInputStream message) {
        short msgId = message.getMsgId();

        switch (msgId) {
            case ShopProtocol.CS_SECURITY_VERIFICATION:
                break;
        }
    }
}