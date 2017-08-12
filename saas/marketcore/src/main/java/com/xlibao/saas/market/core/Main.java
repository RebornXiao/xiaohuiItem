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

        NettyConfig config = initConfig(serverConfig);

        MessageEventListener messageEventListener = MessageEventFactory.getInstance().getMessageEventListener();
        MessageEventListener hexMessageEventListener = MessageEventFactory.getInstance().getHexMessageEventListener();

        // 启动客户端
        NettyClient.getInstance().init(config);
        NettyClient.getInstance().start(serverConfig.getServerIP(), serverConfig.getServerPort(), messageEventListener);

        // 启动服务器
        NettyNetServer.getInstance().init(config);
        NettyNetServer.getInstance().start(serverConfig.getListenerPort(), hexMessageEventListener);
    }

    private static NettyConfig initConfig(ServerConfig serverConfig) {
        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setReadTimeout(serverConfig.getReadTimeout());
        nettyConfig.setWriteTimeout(serverConfig.getWriteTimeout());
        nettyConfig.setBothTimeout(serverConfig.getBothTimeout());

        return nettyConfig;
    }
}