package com.xlibao.saas.market.service.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chinahuangxc on 2017/7/20.
 */
@Component
public class MarketApplicationContextNotify {

    @Autowired
    private MarketDataCacheService marketDataCacheService;

    public void loaderNotify() {
        marketDataCacheService.initMarketCache();
    }
}