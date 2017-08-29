package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.datacache.location.LocationDataCacheService;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.data.model.MarketRelationship;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketAccessLogger;
import com.xlibao.saas.market.service.market.*;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("marketService")
public class MarketServiceImpl extends BasicWebService implements MarketService {

    private static final Logger logger = LoggerFactory.getLogger(MarketServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

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
        if (marketEntry == null) {
            logger.error("没有找到指定的商店(market id:" + marketId + ", passport id:" + passportId + ")，尝试获取上次访问的商店");
            MarketAccessLogger accessLogger = dataAccessFactory.getMarketDataAccessManager().getLastAccessLogger(passportId);
            if (accessLogger != null) { // 寻找上次访问的商店
                logger.info("找到用户最后一次访问的商店(market id:" + accessLogger.getMarketId() + ", passport id:" + passportId + ")");
                marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(accessLogger.getMarketId());
                findType = MarketFindTypeEnum.RECENT_ACCESS;
            }
        }
        if (marketEntry == null) {
            logger.error("没法找到任何一个适合用户的商店(passport id:" + passportId + ")，用户只能通过过滤方式进行选择商店");
            // 1000 -- 您所在区域暂时未找到合适的商店
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response();
        }
        JSONObject response = marketEntry.message(latitude, longitude);
        response.put("findType", findType.getKey());
        return success(response);
    }

    @Override
    public JSONObject filterMarket() {
        int type = getIntParameter("type", ChoiceMarketTypeEnum.PROVINCE.getKey());
        long parentId = getLongParameter("parentId", 0);

        double longitude = getDoubleParameter("longitude", 0.0);
        double latitude = getDoubleParameter("latitude", 0.0);

        if (type == ChoiceMarketTypeEnum.PROVINCE.getKey()) {
            return success(locationMessage(LocationDataCacheService.getProvinces()));
        }
        if (type == ChoiceMarketTypeEnum.CITY.getKey()) {
            return success(locationMessage(LocationDataCacheService.getCitys(parentId)));
        }
        if (type == ChoiceMarketTypeEnum.DISTRICT.getKey()) {
            return success(locationMessage(LocationDataCacheService.getDistricts(parentId)));
        }
        if (type == ChoiceMarketTypeEnum.STREET.getKey()) {
            return success(locationMessage(LocationDataCacheService.getStreets(parentId)));
        }
        if (type == ChoiceMarketTypeEnum.MARKET.getKey()) {
            List<MarketEntry> marketEntries = dataAccessFactory.getMarketDataCacheService().getMarkets(parentId);
            JSONArray response = marketEntries.stream().map(marketEntry -> marketEntry.message(latitude, longitude)).collect(Collectors.toCollection(JSONArray::new));
            return success(response);
        }
        // 1001 -- 错误的商店信息
        return MarketErrorCodeEnum.ERROR_MARKET_INFORMATION.response();
    }

    @Override
    public JSONObject showMarkets() {
        // long passportId = getLongParameter("passportId", 0);
        double longitude = getDoubleParameter("longitude", 0.0);
        double latitude = getDoubleParameter("latitude", 0.0);

        List<MarketEntry> marketEntries = dataAccessFactory.getMarketDataCacheService().findRecentMarket(longitude, latitude);
        if (CommonUtils.isEmpty(marketEntries)) {
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response();
        }
        JSONArray response = marketEntries.stream().map(marketEntry -> marketEntry.message(0, 0)).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject initShelvesDatas() {
        long marketId = getLongParameter("marketId");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);

        String content = HardwareMessageType.SHELVES + "CC";
        marketShopRemoteService.shelvesMessage(marketEntry.getPassportId(), content);
        return success();
    }

    @Override
    public JSONObject searchMarkets() {
        String province = getUTF("province", null);
        String city = getUTF("city", null);
        String district = getUTF("district", null);
        String street = getUTF("street", null);
        long streetId = getIntParameter("streetId", -1);

        int type = getIntParameter("type", -1);
        int status = getIntParameter("status", -1);
        int deliveryMode = getIntParameter("deliveryMode", -1);

        MarketEntry searchModel = new MarketEntry();
        searchModel.setProvince(province);
        searchModel.setCity(city);
        searchModel.setDistrict(district);
        searchModel.setStreet(street);
        searchModel.setStreetId(streetId);
        searchModel.setType(type);
        searchModel.setStatus(status);
        searchModel.setDeliveryMode(deliveryMode);

        int pageSize = getPageSize();//必须分页
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        List<MarketEntry> marketEntries = dataAccessFactory.getMarketDataAccessManager().searchMarkets(searchModel, pageSize, pageStartIndex);
        int count = dataAccessFactory.getMarketDataAccessManager().searchMarketsCount(searchModel);

        JSONObject response = new JSONObject();
        response.put("data", marketEntries);
        response.put("count", count);

        return success(response);
    }

    public JSONObject getMarket() {
        long marketId = getLongParameter("id", 0);
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);
        if (marketEntry == null) {
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response("找不到店铺记录，店铺ID：" + marketId);
        }
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(marketEntry));
        return success(response);
    }

    public JSONObject getAllMarkets() {
        List<MarketEntry> marketEntries = dataAccessFactory.getMarketDataCacheService().getMarketEntries();
        if (CommonUtils.isEmpty(marketEntries)) {
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response("系统不存在商店记录，请联系管理员！");
        }
        return success(marketEntries);
    }

    @Override
    public JSONObject myFocusMarkets() {
        long passportId = getLongParameter("passportId");
        List<MarketRelationship> marketRelationships = dataAccessFactory.getMarketDataAccessManager().myFocusMarkets(passportId, MarketRelationshipTypeEnum.FOCUS.getKey());
        if (CommonUtils.isEmpty(marketRelationships)) {
            return MarketErrorCodeEnum.CAN_NOT_FOUND_FOCUS_RELATIONSHIP.response("您的帐号未绑定任何商店，请联系管理员！");
        }
        JSONArray response = new JSONArray();
        for (MarketRelationship marketRelationship : marketRelationships) {
            MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketRelationship.getMarketId());
            if (marketEntry == null) {
                continue;
            }
            response.add(marketEntry.message(Double.parseDouble(marketEntry.getLocation().split(CommonUtils.SPLIT_COMMA)[1]), Double.parseDouble(marketEntry.getLocation().split(CommonUtils.SPLIT_COMMA)[0])));
        }
        return success(response);
    }

    private <T> JSONArray locationMessage(List<T> t) {
        return t.stream().map(JSONObject::toJSONString).collect(Collectors.toCollection(JSONArray::new));
    }
}