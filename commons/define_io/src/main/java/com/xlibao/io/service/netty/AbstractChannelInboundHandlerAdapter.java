package com.xlibao.io.service.netty;

import com.xlibao.io.entry.MessageInputStream;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chinahuangxc on 2017/8/7.
 */
public class AbstractChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChannelInboundHandlerAdapter.class);

    private static final AttributeKey<NettySession> attributeKey = AttributeKey.valueOf("nettySession");

    private MessageEventListener messageEventListener;

    private AtomicInteger idleTimes = new AtomicInteger(0);

    void registerMessageEventListener(MessageEventListener messageEventListener) {
        this.messageEventListener = messageEventListener;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext context) throws Exception {
        context.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) throws Exception {
        context.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        context.fireChannelActive();

        Channel channel = context.channel();

        NettySession session = newSession();
        session.init(channel);
        logger.info("激活Socket成功，" + session.netTrack());

        // 保存session 到 channel
        channel.attr(attributeKey).set(session);
        // 通知具体业务
        messageEventListener.notifyChannelActive(session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        context.fireChannelInactive();

        // 通道无效时通知关闭
        NettySession session = getSession(context.channel());

        logger.error("会话通道失效 " + session.netTrack());

        messageEventListener.notifySessionClosed(session);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        context.fireChannelRead(msg);

        idleTimes.set(0);

        NettySession session = getSession(context.channel());
        MessageInputStream message = (MessageInputStream) msg;

        logger.info("消息读取 session id " + session.getId() + ", message id is " + message.getMsgId() + " message content " + message.readUTF());

        messageEventListener.notifyMessageReceived(session, message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {
        context.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object evt) throws Exception {
        context.fireUserEventTriggered(evt);

        NettySession session = getSession(context.channel());
        if (!IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            return;
        }
        IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state() == null) {
            return;
        }
        if (idleTimes.get() >= 3) {
            logger.error(session.netTrack() + " 空闲次数到达上限：" + idleTimes.get());
        }
        logger.warn("会话通道空闲中，" + session.netTrack() + ", idle type " + event.state() + "；当前会话空闲次数：" + idleTimes.get());
        switch (event.state()) {
            case READER_IDLE:
                messageEventListener.notifySessionIdle(session, NettyConfig.TIME_OUT_READER);
                break;
            case WRITER_IDLE:
                messageEventListener.notifySessionIdle(session, NettyConfig.TIME_OUT_WRITER);
                break;
            case ALL_IDLE:
                idleTimes.incrementAndGet();
                messageEventListener.notifySessionIdle(session, NettyConfig.TIME_OUT_BOTH);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext context) throws Exception {
        context.fireChannelWritabilityChanged();
        idleTimes.set(0);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        messageEventListener.notifyExceptionCaught(cause);

        NettySession session = getSession(context.channel());
        if ("远程主机强迫关闭了一个现有的连接。".equals(cause.getMessage())) {
            logger.error("会话通道发生异常：" + session.netTrack() + "，异常信息：" + cause.getMessage());
        } else {
            logger.error("会话通道发生异常：" + session.netTrack(), cause);
        }
        // context.fireExceptionCaught(cause);
    }

    private NettySession getSession(Channel channel) {
        return channel.attr(attributeKey).get();
    }

    private NettySession newSession() {
        return new NettySession();
    }
}