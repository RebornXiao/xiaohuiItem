package com.xlibao.saas.market.core.message.hardware;

import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.io.service.netty.filter.HexMessageDecoder;
import com.xlibao.io.service.netty.filter.HexMessageEncoder;
import com.xlibao.saas.market.core.service.MessageHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class HexMessageEventListenerImpl implements MessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(HexMessageEventListenerImpl.class);

    @Override
    public ByteToMessageDecoder newDecoder() {
        return new HexMessageDecoder();
    }

    @Override
    public MessageToMessageEncoder newEncoder() {
        return new HexMessageEncoder();
    }

    @Override
    public void notifyChannelActive(NettySession session) throws Exception {
        logger.info("作为服务端(与硬件交互) 建立一个Socket监听通道 " + session.netTrack());
    }

    @Override
    public void notifyMessageReceived(NettySession session, MessageInputStream message) throws Exception {
        byte[] bytes = new byte[message.getBodyDataSize()];
        // 填充消息
        message.readBytes(bytes);
        // 硬件消息内容
        String parameters = new String(bytes);

        // 通知消息处理适配器
        MessageHandlerAdapter.getInstance().hardwareMessageExecute(session, parameters);
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