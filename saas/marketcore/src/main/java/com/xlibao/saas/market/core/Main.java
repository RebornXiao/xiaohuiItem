package com.xlibao.saas.market.core;

import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettyClient;
import com.xlibao.io.service.netty.NettyConfig;
import com.xlibao.io.service.netty.NettyNetServer;
import com.xlibao.saas.market.core.config.ServerConfig;
import com.xlibao.saas.market.core.message.MessageEventFactory;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.init(args[0]);

        connectorMarketServer();

        // 硬件业务消息监听器
        MessageEventListener hexMessageEventListener = MessageEventFactory.getInstance().getHexMessageEventListener();

        NettyConfig config = initConfig();

        // 启动服务器
        NettyNetServer.getInstance().init(config);
        NettyNetServer.getInstance().start(ServerConfig.getListenerPort(), hexMessageEventListener);
    }

    public static void connectorMarketServer() throws Exception {
        NettyConfig config = initConfig();
        // 商店业务消息监听器
        MessageEventListener marketMessageEventListener = MessageEventFactory.getInstance().getMarketMessageEventListener();
        // 启动客户端
        NettyClient.getInstance().init(config);
        NettyClient.getInstance().start(ServerConfig.getServerIP(), ServerConfig.getServerPort(), marketMessageEventListener);
    }

    private static NettyConfig initConfig() {
        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setReadTimeout(ServerConfig.getReadTimeout());
        nettyConfig.setWriteTimeout(ServerConfig.getWriteTimeout());
        nettyConfig.setBothTimeout(ServerConfig.getBothTimeout());

        return nettyConfig;
    }
}