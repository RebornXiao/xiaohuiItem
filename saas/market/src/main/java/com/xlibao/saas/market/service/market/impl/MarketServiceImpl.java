package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.data.mapper.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketAccessLogger;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.service.market.ChoiceMarketTypeEnum;
import com.xlibao.saas.market.service.market.MarketErrorCodeEnum;
import com.xlibao.saas.market.service.market.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("marketService")
public class MarketServiceImpl extends BasicWebService implements MarketService {

    private static final Logger logger = LoggerFactory.getLogger(MarketServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;

    @Override
    public JSONObject findMarket() {
        long passportId = getLongParameter("passportId", 0);
        long marketId = getLongParameter("marketId", 0);

        double longitude = getDoubleParameter("longitude", 0.0);
        double latitude = getDoubleParameter("latitude", 0.0);

        MarketEntry marketEntry = null;
        if (marketId != 0) {
            marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);
        }
        if (marketId == 0) {
            MarketAccessLogger accessLogger = dataAccessFactory.getMarketDataAccessManager().getLastAccessLogger(passportId);
            if (accessLogger != null) { // 寻找上次访问的商店
                marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(accessLogger.getMarketId());
            } else { // 获取该地址最近的商店
                marketEntry = dataAccessFactory.getMarketDataCacheService().findRecentMarket(longitude, latitude);
            }
        }
        if (marketEntry == null) {
            /** 1000 -- 您所在区域暂时未找到合适的商店 */
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response();
        }
        return success(marketEntry.message());
    }

    @Override
    public JSONObject filterMarket() {
        int type = getIntParameter("type", ChoiceMarketTypeEnum.PROVINCE.getKey());
        long parentId = getLongParameter("parentId", 0);
        return null;
    }
}
