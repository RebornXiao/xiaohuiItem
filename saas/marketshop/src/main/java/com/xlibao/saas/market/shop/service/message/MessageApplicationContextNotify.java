package com.xlibao.saas.market.shop.service.message;

import com.xlibao.io.service.netty.NettyConfig;
import com.xlibao.io.service.netty.NettyNetServer;
import com.xlibao.saas.market.shop.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chinahuangxc on 2017/8/8.
 */
@Component
public class MessageApplicationContextNotify {

    @Autowired
    private MessageEventListenerImpl messageEventListener;

    public void init() {
        NettyConfig nettyConfig = new NettyConfig();
        nettyConfig.setReadOutTime(Integer.parseInt(ConfigFactory.getSocketService().getReadOutTime()));
        nettyConfig.setWriteOutTime(Integer.parseInt(ConfigFactory.getSocketService().getWriteOutTime()));
        nettyConfig.setBothOutTime(Integer.parseInt(ConfigFactory.getSocketService().getBothOutTime()));

        try {
            NettyNetServer.getInstance().init(nettyConfig);
            // 启动Netty服务
            NettyNetServer.getInstance().start(Integer.parseInt(ConfigFactory.getSocketService().getServicePort()), messageEventListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}