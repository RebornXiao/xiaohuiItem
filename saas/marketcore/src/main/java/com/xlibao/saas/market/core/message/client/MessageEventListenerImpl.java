package com.xlibao.saas.market.core.message.client;

import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.io.service.netty.filter.DefaultMessageDecoder;
import com.xlibao.io.service.netty.filter.DefaultMessageEncoder;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class MessageEventListenerImpl implements MessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventListenerImpl.class);

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
        logger.info("作为客户端(与业务服务交互) 建立一个Socket通道 " + session.netTrack());
    }

    @Override
    public void notifyMessageReceived(NettySession session, MessageInputStream message) throws Exception {
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