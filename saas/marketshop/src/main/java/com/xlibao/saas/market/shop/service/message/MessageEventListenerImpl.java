package com.xlibao.saas.market.shop.service.message;

import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.io.service.netty.filter.DefaultMessageDecoder;
import com.xlibao.io.service.netty.filter.DefaultMessageEncoder;
import com.xlibao.saas.market.shop.service.MessageHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chinahuangxc on 2017/8/8.
 */
@Component
public class MessageEventListenerImpl implements MessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventListenerImpl.class);

    @Autowired
    private MessageHandlerAdapter messageHandlerAdapter;

    @Override
    public ByteToMessageDecoder newDecoder() {
        return new DefaultMessageDecoder();
    }

    @Override
    public MessageToMessageEncoder newEncoder() {
        return new DefaultMessageEncoder();
    }

    @Override
    public void notifyChannelActive(NettySession session) throws Exception {
        logger.info("建立一个Socket通道 " + session.netTrack());
    }

    @Override
    public void notifyMessageReceived(NettySession session, MessageInputStream message) throws Exception {
        byte msgType = message.getMsgType();
        if (msgType == MessageFactory.MSG_TYPE_INTERNAL) { // 商店消息
            messageHandlerAdapter.shopMessageExecute(session, message);
            return;
        }
        if (msgType == MessageFactory.MSG_TYPE_PLATFORM) {
            messageHandlerAdapter.platformMessageExecute(session, message);
            return;
        }
        if (msgType == MessageFactory.MSG_TYPE_LOGIC) { // 逻辑消息
            messageHandlerAdapter.logicMessageExecute(session, message);
        }
    }

    @Override
    public void notifySessionClosed(NettySession session) {
    }

    @Override
    public void notifyExceptionCaught(Throwable cause) {
    }

    @Override
    public void notifySessionIdle(NettySession session, int idleType) {
    }
}