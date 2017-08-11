package com.xlibao.saas.market.shop.service;

import com.xlibao.common.exception.PlatformErrorCodeEnum;
import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.saas.market.shop.service.shop.ShopProtocol;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chinahuangxc on 2017/8/9.
 */
@Component
public class MarketSessionManager {

    private static final Map<Long, NettySession> marketSessions = new ConcurrentHashMap<>();

    public void put(long passportId, NettySession session) {
        // 不管原来是否存在 都直接存放(若原来存在 则替换)
        marketSessions.put(passportId, session);
    }

    public void sendHardwarePush(long passportId, String content) {
        NettySession session = marketSessions.get(passportId);
        if (session == null) {
            PlatformErrorCodeEnum.NOT_FOUND_TARGET.throwException();
        }
        MessageOutputStream message = MessageFactory.createInternalMessage(ShopProtocol.CS_HARDWARE);
        message.writeUTF(content);

        session.send(message);
    }
}