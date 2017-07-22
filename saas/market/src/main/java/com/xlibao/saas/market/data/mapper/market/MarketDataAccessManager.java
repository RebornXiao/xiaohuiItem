package com.xlibao.saas.market.data.mapper.market;

import com.xlibao.saas.market.data.model.MarketAccessLogger;
import com.xlibao.saas.market.data.model.MarketEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Component
public class MarketDataAccessManager {

    @Autowired
    private MarketEntryMapper entryMapper;
    @Autowired
    private MarketCabinetLoggerMapper cabinetLoggerMapper;
    @Autowired
    private MarketCabinetMapper cabinetMapper;
    @Autowired
    private MarketAccessLoggerMapper accessLoggerMapper;

    public List<MarketEntry> loaderMarkets() {
        return entryMapper.loaderMarkets();
    }

    public MarketEntry getMarket(long marketId) {
        return entryMapper.getMarket(marketId);
    }

    public List<MarketEntry> getMarkets(long streetId) {
        return entryMapper.getMarkets(streetId);
    }

    public MarketEntry getMarketForPassport(long passportId) {
        return entryMapper.getMarketForPassport(passportId);
    }

    public MarketAccessLogger getLastAccessLogger(long passportId) {
        return accessLoggerMapper.getLastAccessLogger(passportId);
    }
}