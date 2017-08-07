/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlibao.io.service.netty;

import com.xlibao.io.service.netty.filter.NettyDecoder;
import com.xlibao.io.service.netty.filter.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NettyServer {

    private static Logger log = LoggerFactory.getLogger(NettyServer.class);

    final AttributeKey<NettySession> SKEY = AttributeKey.valueOf("NS");
    boolean runing = false;
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ServerBootstrap b;
    NettyConfig config;

    public void init(NettyConfig config) {
        this.config = config;
    }

    public void start(int port) throws Exception {
        if (runing) {
            return;
        }
        try {
            runing = true;
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            b = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).localAddress(port).childHandler(this.channelInitializer);
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();
            log.info("服务器启动成功！");
        } catch (Exception e) {
            runing = false;
            throw e;
        }
    }

    public void close() {
        if (!runing) {
            return;
        }
        try {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
            runing = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            bossGroup = null;
            workerGroup = null;
        }
    }

    public boolean isRuning() {
        return runing;
    }

    private final ChannelInitializer channelInitializer = new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline cp = ch.pipeline();
            cp.addLast(new NettyDecoder()).addLast(new NettyEncoder());

            if (config != null) {
                cp.addLast(new IdleStateHandler(config.readOutTime, config.writeOutTime, config.allOutTime, TimeUnit.SECONDS));
            }
            cp.addLast(new ChannelInboundHandlerAdapter() {

                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    super.channelActive(ctx);
                    NettySession ns = createNettySession();
                    ns.init(ctx.channel());
//                    log.info("channelActive 连接进入, " + ns.getSessionId());
//                    //如果需要被拦截
//                    for (int i = 0; i < createrInterceptors.size(); i++) {
//                        if (createrInterceptors.get(i).onCreater(ns)) {
//                            //直接关闭
//                            log.info("拦截了 连接进入, " + ns.getSessionId());
//                            ns.close();
//                            return;
//                        }
//                    }
//                    ctx.channel().attr(SKEY).set(ns);//保存到channel
//                    for (int i = 0; i < listeners.size(); i++) {
//                        listeners.get(i).onSessionEnter(ns);
//                    }
                }

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    NettySession ns = toNettySession(ctx.channel());
//                    log.info("channelRead 消息读取 " + msg + ", " + ns.getSessionId());
//                    Message message = (Message) msg;
//                    for (int i = 0; i < messageInterceptors.size(); i++) {
//                        if (messageInterceptors.get(i).onMessage(ns, message)) {
//                            //直接关闭
//                            ns.close();
//                            return;
//                        }
//                    }
//                    //直接执行
//                    if (executorMgr != null) {
//                        MessageExecutor executor = executorMgr.getExecutor(message.getId());
//                        if (executor != null) {
//                            try {
//                                executor.executor(ns, msg);
//                            } catch (Throwable e) {
//                                log.error("executor handler error , id = " + message.getId());
//                                e.printStackTrace();
//                            }
//                        } else {
//                            log.error("executor is null, id = " + message.getId());
//                        }
//                    }
//                    //直接执行msg
//                    for (int i = 0; i < listeners.size(); i++) {
//                        listeners.get(i).onSessionMessage(ns, message);
//                    }
                }

                @Override
                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                    //启动成功通知
                    NettySession ns = toNettySession(ctx.channel());
//                    log.info("channelInactive 连接离开 " + ns.getSessionId());
//                    for (int i = 0; i < listeners.size(); i++) {
//                        listeners.get(i).onSessionExit(ns);
//                    }
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                        throws Exception {
                    cause.printStackTrace();
                    log.error("exceptionCaught 异常" + ", " + ctx.channel());
//                    for (int i = 0; i < listeners.size(); i++) {
//                        listeners.get(i).onException(cause, 0);
//                    }
                    ctx.fireExceptionCaught(cause);
                    ctx.close();
                }

                @Override
                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                    NettySession ns = toNettySession(ctx.channel());
                    if (!IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
                        return;
                    }
                    IdleStateEvent event = (IdleStateEvent) evt;
                    if (null == event.state()) {
                        return;
                    }
                    switch (event.state()) {
                        case READER_IDLE:
//                            log.warn("read idle " + ns.getSessionId());
//                            System.out.println("read idle " + ns.getSessionId());
//                            for (int i = 0; i < listeners.size(); i++) {
//                                listeners.get(i).onSessionIdle(ns, ConnectionSession.TIME_OUT_READER);
//                            }
                            break;
                        case WRITER_IDLE:
//                            log.warn("write idle " + ns.getSessionId());
//                            for (int i = 0; i < listeners.size(); i++) {
//                                listeners.get(i).onSessionIdle(ns, ConnectionSession.TIME_OUT_WRITER);
//                            }
                            break;
                        case ALL_IDLE:
//                            log.warn("all idle " + ns.getSessionId());
//                            for (int i = 0; i < listeners.size(); i++) {
//                                listeners.get(i).onSessionIdle(ns, ConnectionSession.TIME_OUT_ALL);
//                            }
                            break;
                        default:
//                            log.warn("未知 idle " + ns.getSessionId());
                            break;
                    }
                }
            });
        }
    };

    public NettySession createNettySession() {
        return new NettySession();
    }

    private NettySession toNettySession(Channel channel) {
        return channel.attr(SKEY).get();
    }
}
