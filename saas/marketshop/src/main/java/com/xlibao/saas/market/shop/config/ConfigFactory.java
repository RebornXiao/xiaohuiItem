package com.xlibao.saas.market.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * @author chinahuangxc on 2017/8/8.
 */
@Configuration
@Lazy(false)
public class ConfigFactory {

    @Autowired
    private SocketServiceConfig socketServiceConfig;

    private static SocketServiceConfig socketService;

    @PostConstruct
    public void initialization() {
        socketService = socketServiceConfig;
    }

    public static SocketServiceConfig getSocketService() {
        return socketService;
    }
}