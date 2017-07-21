package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.data.mapper.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketAccessLogger;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.service.market.ChoiceMarketTypeEnum;
import com.xlibao.saas.market.service.market.MarketErrorCodeEnum;
import com.xlibao.saas.market.service.market.MarketFindTypeEnum;
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
        MarketFindTypeEnum findType = MarketFindTypeEnum.CLIENT_PROVIDE;
        if (marketId != 0) {
            marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);
        }
        if (marketId == 0) {
            logger.error("没有找到指定的商店(market id:" + marketId + ", passport id:" + passportId + ")，尝试获取上次访问的商店");
            MarketAccessLogger accessLogger = dataAccessFactory.getMarketDataAccessManager().getLastAccessLogger(passportId);
            if (accessLogger != null) { // 寻找上次访问的商店
                logger.info("找到用户最后一次访问的商店(market id:" + accessLogger.getMarketId() + ", passport id:" + passportId + ")");
                marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(accessLogger.getMarketId());
                findType = MarketFindTypeEnum.RECENT_ACCESS;
            } else { // 获取该地址最近的商店
                logger.error("没法获取用户最后一次访问的商店(passport id:" + passportId + ")，尝试查找附近距离用户最近且在服务范围内商店(" + longitude + ", " + latitude + ")");
                marketEntry = dataAccessFactory.getMarketDataCacheService().findRecentMarket(longitude, latitude);
                findType = MarketFindTypeEnum.LOCATION;
            }
        }
        if (marketEntry == null) {
            logger.error("没法找到任何一个适合用户的商店(passport id:" + passportId + ")，用户只能通过过滤方式进行选择商店");
            /** 1000 -- 您所在区域暂时未找到合适的商店 */
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response();
        }
        JSONObject response = marketEntry.message();
        response.put("findType", findType.getKey());
        return success(response);
    }

    @Override
    public JSONObject filterMarket() {
        int type = getIntParameter("type", ChoiceMarketTypeEnum.PROVINCE.getKey());
        long parentId = getLongParameter("parentId", 0);
        return null;
    }
}
