package com.xlibao.saas.market.shop.service.message.example;

import com.xlibao.io.service.netty.NettyConfig;
import com.xlibao.io.service.netty.NettyNetServer;
import com.xlibao.saas.market.shop.service.message.MessageEventListenerImpl;

/**
 * @author chinahuangxc on 2017/8/8.
 */
public class NettyExample {

    public static void main(String[] args) throws Exception {
        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setReadOutTime(10);
        nettyConfig.setWriteOutTime(10);
        nettyConfig.setBothOutTime(15);

        NettyNetServer.getInstance().init(nettyConfig);
        NettyNetServer.getInstance().start(9527, new MessageEventListenerImpl());
    }
}