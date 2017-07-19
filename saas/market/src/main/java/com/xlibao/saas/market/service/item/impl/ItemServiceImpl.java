package com.xlibao.saas.market.service.item.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.lbs.baidu.AddressComponent;
import com.xlibao.common.lbs.baidu.BaiduWebAPI;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemType;
import com.xlibao.saas.market.data.mapper.DataAccessFactory;
import com.xlibao.saas.market.data.model.*;
import com.xlibao.saas.market.service.activity.RecommendItemTypeEnum;
import com.xlibao.saas.market.service.item.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("itemService")
public class ItemServiceImpl extends BasicWebService implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;

    @Override
    public JSONObject homepage() {
        long passportId = getLongParameter("passportId", 0);
        long marketId = getLongParameter("marketId", 0);

        double longitude = getDoubleParameter("longitude", 0.0);
        double latitude = getDoubleParameter("latitude", 0.0);

        if (marketId == 0) { // 找到上次访问的商店
            MarketAccessLogger accessLogger = dataAccessFactory.getMarketDataAccessManager().getLastAccessLogger(passportId);
            if (accessLogger != null) {
                marketId = accessLogger.getMarketId();
            }
        }
        if (marketId == 0) { // 根据当前位置找到最近的商店
            MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().findRecentMarket(longitude, latitude);
            if (marketEntry != null) {
                marketId = marketEntry.getId();
            }
        }
        // Banner
        JSONArray bannerMsg = bannerMsg(marketId, latitude, longitude);
        // SpecialButton
        JSONArray specialButtonMsg = specialButtonMsg();
        // RecommendItemTypes
        JSONArray recommendItemTypeMsg = recommendMsg(marketId, RecommendItemTypeEnum.ITEM_TYPE.getKey(), latitude, longitude);
        // RecommendItems
        JSONArray recommendItemMsg = recommendMsg(marketId, RecommendItemTypeEnum.ITEM.getKey(), latitude, longitude);

        JSONObject response = new JSONObject();
        response.put("bannerMsg", bannerMsg);
        response.put("specialButtonMsg", specialButtonMsg);
        response.put("recommendItemTypeMsg", recommendItemTypeMsg);
        response.put("recommendItemMsg", recommendItemMsg);
        return success(response);
    }

    @Override
    public JSONObject itemTypes() {
        return null;
    }

    @Override
    public JSONObject pageItems() {

        return null;
    }

    private JSONArray specialButtonMsg() {
        List<MarketSpecialButton> buttons = dataAccessFactory.getItemDataAccessManager().getButtons();
        if (CommonUtils.isEmpty(buttons)) {
            return new JSONArray();
        }
        JSONArray response = new JSONArray();
        for (MarketSpecialButton button : buttons) {
            JSONObject data = new JSONObject();
            data.put("id", button.getId());
            data.put("name", button.getName());
            data.put("icon", button.getImage());
            data.put("type", button.getType());
            data.put("jumpUrl", button.getJumpUrl());

            response.add(data);
        }
        return response;
    }

    private JSONArray bannerMsg(long marketId, double latitude, double longitude) {
        List<MarketBanner> banners = dataAccessFactory.getActivityDataAccessManager().getBannerByMarket(marketId);
        logger.info("通过商店ID(" + marketId + ")获取Banner数量为：" + (banners == null ? 0 : banners.size()));
        if (CommonUtils.isEmpty(banners)) {
            logger.error("无法通过商店ID(" + marketId + ")获取Banner记录，尝试通过定位方式来获取，经纬度为：" + longitude + "," + latitude);
            AddressComponent addressComponent = BaiduWebAPI.geocoding(latitude, longitude);
            logger.info("通过经纬度(" + longitude + "," + latitude + ")获取地址信息为：" + addressComponent);
            if (addressComponent != null) {
                String adcode = addressComponent.getAdcode();
                banners = dataAccessFactory.getActivityDataAccessManager().getAdcodeDefaultBanners(adcode);
                logger.info("通过地址信息(" + addressComponent + ")获取Banner数量为：" + (banners == null ? 0 : banners.size()));
            }
        }
        if (CommonUtils.isEmpty(banners)) { // 直接获取系统的默认banner
            logger.error("通过经纬度(" + longitude + "," + latitude + ")信息无法获取到Banner记录，尝试获取默认的Banner记录");
            banners = dataAccessFactory.getActivityDataAccessManager().getDefaultBanners();
            if (CommonUtils.isEmpty(banners)) {
                logger.info("无法获取默认的Banner记录");
            }
        }
        JSONArray response = new JSONArray();
        if (!CommonUtils.isEmpty(banners)) {
            for (MarketBanner banner : banners) {
                JSONObject bannerMsg = new JSONObject();

                bannerMsg.put("id", banner.getId());
                bannerMsg.put("imageURL", banner.getImageUrl());
                bannerMsg.put("type", banner.getType());
                bannerMsg.put("clickURL", banner.getClickUrl());

                response.add(bannerMsg);
            }
        }
        return response;
    }

    private JSONArray recommendMsg(long marketId, int type, double latitude, double longitude) {
        RecommendItemTypeEnum recommendItemTypeEnum = RecommendItemTypeEnum.getRecommendItemTypeEnum(type);

        List<MarketRecommendItem> recommendItems = dataAccessFactory.getActivityDataAccessManager().getRecommendItemsByMarket(marketId, type);
        logger.info("通过商店ID(" + marketId + ")获取推荐" + recommendItemTypeEnum.getValue() + "数量为：" + (recommendItems == null ? 0 : recommendItems.size()));

        if (CommonUtils.isEmpty(recommendItems)) {
            logger.error("无法通过商店ID(" + marketId + ")获取推荐" + recommendItemTypeEnum.getValue() + "记录，尝试通过定位方式来获取，经纬度为：" + longitude + "," + latitude);
            AddressComponent addressComponent = BaiduWebAPI.geocoding(latitude, longitude);
            logger.info("通过经纬度(" + longitude + "," + latitude + ")获取地址信息为：" + addressComponent);
            if (addressComponent != null) {
                String adcode = addressComponent.getAdcode();
                recommendItems = dataAccessFactory.getActivityDataAccessManager().getAdcodeDefaultRecommendItems(adcode, type);
                logger.info("通过地址信息(" + addressComponent + ")获取推荐" + recommendItemTypeEnum.getValue() + "数量为：" + (recommendItems == null ? 0 : recommendItems.size()));
            }
        }
        if (CommonUtils.isEmpty(recommendItems)) { // 直接获取系统的默认banner
            logger.error("通过经纬度(" + longitude + "," + latitude + ")信息无法获取推荐" + recommendItemTypeEnum.getValue() + "记录，尝试获取默认的Banner记录");
            recommendItems = dataAccessFactory.getActivityDataAccessManager().getDefaultRecommendItems(type);
            if (CommonUtils.isEmpty(recommendItems)) {
                logger.info("无法获取默认的推荐" + recommendItemTypeEnum.getValue() + "记录");
            }
        }
        JSONArray response = new JSONArray();
        if (!CommonUtils.isEmpty(recommendItems)) {
            for (MarketRecommendItem recommendItem : recommendItems) {
                JSONObject recommendItemMsg = new JSONObject();

                long entryId = recommendItem.getEntryId();

                if (recommendItemTypeEnum == RecommendItemTypeEnum.ITEM_TYPE) {
                    ItemType itemType = ItemDataCacheService.getItemType(entryId);
                    if (itemType == null) {
                        continue;
                    }
                    recommendItemMsg.put("id", itemType.getId());
                    recommendItemMsg.put("name", itemType.getTitle());
                    recommendItemMsg.put("icon", itemType.getIcon());
                    recommendItemMsg.put("image", itemType.getImage());

                    response.add(recommendItemMsg);
                    continue;
                }
                ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(entryId);
                if (itemTemplate == null) {
                    continue;
                }
            }
        }
        return response;
    }
}