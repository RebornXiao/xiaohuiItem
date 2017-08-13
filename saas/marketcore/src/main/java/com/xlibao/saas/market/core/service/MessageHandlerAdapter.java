package com.xlibao.saas.market.core.service;

import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.ShopProtocol;
import com.xlibao.saas.market.core.message.SessionManager;
import com.xlibao.saas.market.core.service.support.SupportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/9.
 */
public class MessageHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerAdapter.class);

    private static final MessageHandlerAdapter instance = new MessageHandlerAdapter();

    private MessageHandlerAdapter() {
    }

    public static MessageHandlerAdapter getInstance() {
        return instance;
    }

    public void hardwareMessageExecute(NettySession session, String content) {
        String messageType = content.substring(4, 8);
        logger.info("Message type is " + messageType + "; " + session.netTrack());

        MessageOutputStream message = MessageFactory.createInternalMessage(ShopProtocol.CS_HARDWARE);
        message.writeUTF(content);

        SessionManager.getInstance().sendLogicSession(message);
    }

    public void marketMessageExecute(NettySession session, MessageInputStream message) {
        byte messageType = message.getMsgType();

        if (messageType == MessageFactory.MSG_TYPE_INTERNAL) { // 硬件消息
            SupportFactory.getInstance().getApplicationService().hardwareMessageExecute(session, message);
            return;
        }
        if (messageType == MessageFactory.MSG_TYPE_PLATFORM) { // 心跳消息
            SupportFactory.getInstance().getApplicationService().platformMessageExecute(session, message);
            return;
        }
        if (messageType == MessageFactory.MSG_TYPE_LOGIC) { // 逻辑消息
            SupportFactory.getInstance().getApplicationService().logicMessageExecute(session, message);
        }
    }
}