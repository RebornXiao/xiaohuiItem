package com.xlibao.saas.market.shop.service;

import com.xlibao.io.service.netty.NettySession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chinahuangxc on 2017/8/9.
 */
@Component
public class MarketSessionManager {

    private static final Map<Long, NettySession> marketSessions = new ConcurrentHashMap<>();


}