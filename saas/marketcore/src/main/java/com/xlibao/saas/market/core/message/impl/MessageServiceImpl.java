package com.xlibao.saas.market.core.message.impl;

import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettyClient;
import com.xlibao.io.service.netty.NettyConfig;
import com.xlibao.io.service.netty.NettyNetServer;
import com.xlibao.saas.market.core.config.ConfigFactory;
import com.xlibao.saas.market.core.message.MessageEventFactory;
import com.xlibao.saas.market.core.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/19.
 */
@Transactional
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageEventFactory messageEventFactory;

    public boolean connectorMarketServer() {
        NettyConfig config = ConfigFactory.newNettyConfig();
        // 商店业务消息监听器
        MessageEventListener marketMessageEventListener = messageEventFactory.getMarketMessageEventListener();
        // 启动客户端
        NettyClient.getInstance().init(config);
        try {
            NettyClient.getInstance().start(ConfigFactory.getServer().getServerIP(), ConfigFactory.getServer().getServerPort(), marketMessageEventListener);
            return true;
        } catch (Exception ex) {
            ex.getMessage();
        }
        return false;
    }

    @Override
    public void startListener() {
        // 硬件业务消息监听器
        MessageEventListener hexMessageEventListener = messageEventFactory.getHexMessageEventListener();

        NettyConfig config = ConfigFactory.newNettyConfig();
        // 启动服务器
        NettyNetServer.getInstance().init(config);
        try {
            NettyNetServer.getInstance().start(ConfigFactory.getServer().getListenerPort(), hexMessageEventListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
