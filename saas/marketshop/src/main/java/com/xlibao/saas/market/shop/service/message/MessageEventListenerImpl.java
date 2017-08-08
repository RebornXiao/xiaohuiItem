package com.xlibao.saas.market.shop.service.message;

import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.io.service.netty.filter.DefaultMessageDecoder;
import com.xlibao.io.service.netty.filter.DefaultMessageEncoder;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * @author chinahuangxc on 2017/8/8.
 */
public class MessageEventListenerImpl implements MessageEventListener {

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