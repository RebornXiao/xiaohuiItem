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

/**
 * @author chinahuangxc on 2017/8/7.
 */
public abstract class AbstractChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChannelInboundHandlerAdapter.class);

    private static final AttributeKey<NettySession> attributeKey = AttributeKey.valueOf("nettySession");

    public static final int TIME_OUT_READER = 0;
    public static final int TIME_OUT_WRITER = 1;
    public static final int TIME_OUT_BOTH = 2;

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
        logger.info("通道激活成功 session id " + session.getId());

        // 保存session 到 channel
        channel.attr(attributeKey).set(session);
        // 通知具体业务
        notifyChannelActive(session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        context.fireChannelInactive();

        // 通道无效时通知关闭
        NettySession session = getSession(context.channel());

        logger.error("通道失效了 session id " + session.getId());

        notifySessionClosed(session);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        context.fireChannelRead(msg);

        NettySession session = getSession(context.channel());

        logger.info("消息读取 session id " + session.getId() + ", message " + msg);

        MessageInputStream message = (MessageInputStream) msg;
        notifyMessageReceived(session, message);
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
        logger.warn("Session idle " + session.getId() + ", idle type " + event.state());
        switch (event.state()) {
            case READER_IDLE:
                notifySessionIdle(session, TIME_OUT_READER);
                break;
            case WRITER_IDLE:
                notifySessionIdle(session, TIME_OUT_WRITER);
                break;
            case ALL_IDLE:
                notifySessionIdle(session, TIME_OUT_BOTH);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext context) throws Exception {
        context.fireChannelWritabilityChanged();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        context.fireExceptionCaught(cause);

        notifyExceptionCaught(cause);

        logger.error("通话通道发生异常：" + context.channel(), cause);

        context.fireExceptionCaught(cause);
    }

    private NettySession getSession(Channel channel) {
        return channel.attr(attributeKey).get();
    }

    private NettySession newSession() {
        return new NettySession();
    }

    abstract void notifyChannelActive(NettySession session) throws Exception;

    abstract void notifyMessageReceived(NettySession session, MessageInputStream message) throws Exception;

    abstract void notifySessionClosed(NettySession session);

    abstract void notifyExceptionCaught(Throwable cause);

    abstract void notifySessionIdle(NettySession session, int idleType);
}