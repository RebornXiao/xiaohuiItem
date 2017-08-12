package com.xlibao.saas.market.core.message.client;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class MarketClient {

    private static final MarketClient instance = new MarketClient();

    private MarketClient() {
    }

    public static MarketClient getInstance() {
        return instance;
    }
}