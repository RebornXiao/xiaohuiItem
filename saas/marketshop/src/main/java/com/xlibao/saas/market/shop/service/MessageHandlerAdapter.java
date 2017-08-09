package com.xlibao.saas.market.shop.service;

import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.saas.market.shop.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chinahuangxc on 2017/8/9.
 */
@Component
public class MessageHandlerAdapter {

    @Autowired
    private ShopService shopService;

    public void shopMessageExecute(NettySession session, MessageInputStream message) {
        int msgId = message.getMsgId();

        MessageOutputStream output = null;
        switch (msgId) {
            case 0:
                output = shopService.securityVerification(message);
                break;
        }
        session.send(output);
    }

    public void logicMessageExecute(NettySession session, MessageInputStream message) {
    }
}