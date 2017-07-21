package com.xlibao.saas.market.service.item.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.lbs.baidu.AddressComponent;
import com.xlibao.common.lbs.baidu.BaiduWebAPI;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemType;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.data.mapper.DataAccessFactory;
import com.xlibao.saas.market.data.model.*;
import com.xlibao.saas.market.service.ErrorCodeEnum;
import com.xlibao.saas.market.service.XMarketTimeConfig;
import com.xlibao.saas.market.service.activity.RecommendItemTypeEnum;
import com.xlibao.saas.market.service.item.*;
import com.xlibao.saas.market.service.market.MarketErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        // 匹配可用的商店ID
        marketId = matchMarketId(passportId, marketId, longitude, latitude);

        // Banner
        JSONArray bannerMsg = bannerMsg(marketId, latitude, longitude);
        // SpecialButton
        JSONArray specialButtonMsg = specialButtonMsg();
        // RecommendItemTypes
        JSONArray recommendItemTypeMsg = recommendMsg(marketId, RecommendItemTypeEnum.ITEM_TYPE.getKey(), latitude, longitude, 0, 0);

        JSONObject response = new JSONObject();
        response.put("bannerMsg", bannerMsg);
        response.put("specialButtonMsg", specialButtonMsg);
        response.put("recommendItemTypeMsg", recommendItemTypeMsg);
        return success(response);
    }

    @Override
    public JSONObject itemTypes() {
        List<ItemType> itemTypes = ItemDataCacheService.getItemTypes();

        JSONArray response = fillItemTypeMsg(itemTypes, false);

        return success(response);
    }

    @Override
    public JSONObject findSubItemTypes() {
        long itemTypeId = getLongParameter("itemTypeId");
        ItemType itemType = ItemDataCacheService.getItemType(itemTypeId);
        if (itemType == null) {
            return ItemErrorCodeEnum.ITEM_TYPE_ERROR.response();
        }
        List<ItemType> itemTypes = ItemDataCacheService.relationItemTypes(itemTypeId);
        JSONArray response = fillItemTypeMsg(itemTypes, true);
        return success(response);
    }

    @Override
    public JSONObject findRecommendItems() {
        long passportId = getLongParameter("passportId", 0);
        long marketId = getLongParameter("marketId", 0);

        double longitude = getDoubleParameter("longitude", 0.0);
        double latitude = getDoubleParameter("latitude", 0.0);

        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        marketId = matchMarketId(passportId, marketId, longitude, latitude);
        if (marketId == 0) {
            return MarketErrorCodeEnum.CAN_NOT_FIND_MARKET.response();
        }
        JSONArray recommendItemMsg = recommendMsg(marketId, RecommendItemTypeEnum.ITEM.getKey(), latitude, longitude, pageStartIndex, pageSize);
        return success(recommendItemMsg);
    }

    @Override
    public JSONObject pageItems() {
        long passportId = getLongParameter("passportId", 0);
        long marketId = getLongParameter("marketId");

        long itemTypeId = getLongParameter("itemTypeId", 0);
        int sortType = getIntParameter("sortType", ItemSortEnum.DEFAULT.getKey());
        int sortValue = getIntParameter("sortValue", 0);
        String searchKeyValue = getUTF("searchKeyValue", null);

        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex(pageSize);

        List<MarketItem> items;
        if (CommonUtils.isNotNullString(searchKeyValue)) {
            items = conditionPageItems(marketId, ItemDataCacheService.fuzzyQueryItemTemplates(searchKeyValue), sortType, sortValue, pageStartIndex, pageSize);
        } else {
            ItemSpecialTypeEnum itemSpecialTypeEnum = ItemSpecialTypeEnum.getItemSpecialTypeEnum(itemTypeId);
            if (itemSpecialTypeEnum != null) {
                items = specialPageItems(itemSpecialTypeEnum, marketId, sortType, sortValue, pageStartIndex, pageSize);
            } else {
                items = conditionPageItems(marketId, ItemDataCacheService.appointItemType(itemTypeId), sortType, sortValue, pageStartIndex, pageSize);
            }
        }
        if (CommonUtils.isEmpty(items)) {
            // 没有更多数据
            return ErrorCodeEnum.NO_MORE_DATA.response();
        }
        JSONObject buyMessage = fillBuyMessage(passportId, items);

        JSONObject message = new JSONObject();
        message.put("buyMessage", buyMessage);
        message.put("pageItemMsg", fillPageItemMessage(items));
        return success(message);
    }

    private long matchMarketId(long passportId, long marketId, double longitude, double latitude) {
        if (marketId == 0) { // 若没有指定商店 那么寻找上次访问的商店
            MarketAccessLogger accessLogger = dataAccessFactory.getMarketDataAccessManager().getLastAccessLogger(passportId);
            if (accessLogger != null) {
                marketId = accessLogger.getMarketId();
            }
        }
        if (marketId == 0) { // 若无法找到上次访问的商店 那么根据当前位置找到最近的商店
            MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().findRecentMarket(longitude, latitude);
            if (marketEntry != null) {
                marketId = marketEntry.getId();
            }
        }
        return marketId;
    }

    private JSONArray specialButtonMsg() {
        List<MarketSpecialButton> buttons = dataAccessFactory.getMarketItemDataCacheService().getButtons();
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

    private JSONArray recommendMsg(long marketId, int type, double latitude, double longitude, int pageStartIndex, int pageSize) {
        RecommendItemTypeEnum recommendItemTypeEnum = RecommendItemTypeEnum.getRecommendItemTypeEnum(type);

        List<MarketRecommendItem> recommendItems = findRecommendItems(marketId, recommendItemTypeEnum, longitude, latitude);
        if (CommonUtils.isEmpty(recommendItems)) {
            return new JSONArray();
        }
        JSONArray response = new JSONArray();
        if (recommendItemTypeEnum == RecommendItemTypeEnum.ITEM) {
            if (marketId == 0) { // 仅为保护，实际作用不大
                return response;
            }
            StringBuilder itemTemplateSet = new StringBuilder();
            for (MarketRecommendItem recommendItem : recommendItems) {
                itemTemplateSet.append(recommendItem.getEntryId()).append(CommonUtils.SPLIT_COMMA);
            }
            itemTemplateSet.deleteCharAt(itemTemplateSet.length() - 1);

            return response;
        }
        for (MarketRecommendItem recommendItem : recommendItems) {
            ItemType itemType = ItemDataCacheService.getItemType(recommendItem.getEntryId());
            if (itemType == null) {
                continue;
            }
            response.add(fillItemTypeMsg(itemType, true));
        }
        return response;
    }

    private List<MarketRecommendItem> findRecommendItems(long marketId, RecommendItemTypeEnum recommendItemTypeEnum, double longitude, double latitude) {
        List<MarketRecommendItem> recommendItems = marketId == 0 ? null : dataAccessFactory.getActivityDataAccessManager().getRecommendItemsByMarket(marketId, recommendItemTypeEnum.getKey());
        logger.info("通过商店ID(" + marketId + ")获取推荐" + recommendItemTypeEnum.getValue() + "数量为：" + (recommendItems == null ? 0 : recommendItems.size()));

        if (CommonUtils.isEmpty(recommendItems)) {
            logger.error("无法通过商店ID(" + marketId + ")获取推荐" + recommendItemTypeEnum.getValue() + "记录，尝试通过定位方式来获取，经纬度为：" + longitude + "," + latitude);
            AddressComponent addressComponent = BaiduWebAPI.geocoding(latitude, longitude);
            logger.info("通过经纬度(" + longitude + "," + latitude + ")获取地址信息为：" + addressComponent);
            if (addressComponent != null) {
                String adcode = addressComponent.getAdcode();
                recommendItems = dataAccessFactory.getActivityDataAccessManager().getAdcodeDefaultRecommendItems(adcode, recommendItemTypeEnum.getKey());
                logger.info("通过地址信息(" + addressComponent + ")获取推荐" + recommendItemTypeEnum.getValue() + "数量为：" + (recommendItems == null ? 0 : recommendItems.size()));
            }
        }
        if (CommonUtils.isEmpty(recommendItems)) { // 直接获取系统的默认推荐商品或分类
            logger.error("通过经纬度(" + longitude + "," + latitude + ")信息无法获取推荐" + recommendItemTypeEnum.getValue() + "记录，尝试获取默认的Banner记录");
            recommendItems = dataAccessFactory.getActivityDataAccessManager().getDefaultRecommendItems(recommendItemTypeEnum.getKey());
            if (CommonUtils.isEmpty(recommendItems)) {
                logger.info("无法获取默认的推荐" + recommendItemTypeEnum.getValue() + "记录");
            }
        }
        return recommendItems;
    }

    private JSONArray fillItemTypeMsg(List<ItemType> itemTypes, boolean fillImage) {
        if (CommonUtils.isEmpty(itemTypes)) {
            return new JSONArray();
        }
        return itemTypes.stream().map(itemType -> fillItemTypeMsg(itemType, fillImage)).collect(Collectors.toCollection(JSONArray::new));
    }

    private JSONObject fillItemTypeMsg(ItemType itemType, boolean fillImage) {
        JSONObject response = new JSONObject();

        response.put("id", itemType.getId());
        response.put("name", itemType.getTitle());
        response.put("icon", itemType.getIcon());
        response.put("image", itemType.getImage());
        response.put("fillImage", fillImage ? GlobalAppointmentOptEnum.LOGIC_FALSE.getKey() : GlobalAppointmentOptEnum.LOGIC_TRUE.getKey());

        return response;
    }

    private List<MarketItem> conditionPageItems(long marketId, List<ItemTemplate> itemTemplates, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        if (CommonUtils.isEmpty(itemTemplates)) {
            return null;
        }
        // 拼装商品集合
        String itemTemplateSet = ItemDataCacheService.assemblyItemTemplateSet(itemTemplates);
        // 按前端指定的排序条件查找对应的商品列表
        return dataAccessFactory.getItemDataAccessManager().conditionOrdering(marketId, itemTemplateSet, sortType, sortValue, pageStartIndex, pageSize);
    }

    private List<MarketItem> specialPageItems(ItemSpecialTypeEnum itemSpecialTypeEnum, long marketId, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        return dataAccessFactory.getItemDataAccessManager().specialProducts(marketId, itemSpecialTypeEnum.getKey(), XMarketTimeConfig.ITEM_NEW_TIME, sortType, sortValue, pageStartIndex, pageSize);
    }

    private JSONObject fillBuyMessage(long passportId, List<MarketItem> items) {
        if (CommonUtils.isEmpty(items) || passportId <= 0) {
            return new JSONObject();
        }
        String itemSet = itemSet(items);
        // 获取用户当天的购买记录
        List<MarketItemDailyPurchaseLogger> itemDailyPurchaseLoggers = dataAccessFactory.getItemDataAccessManager().passportDailyBuyLoggers(passportId, itemSet);
        Map<Long, MarketItemDailyPurchaseLogger> itemDailyPurchaseLoggerMap = new HashMap<>();
        if (!CommonUtils.isEmpty(itemDailyPurchaseLoggers)) {
            for (MarketItemDailyPurchaseLogger roleDailyBuyLogger : itemDailyPurchaseLoggers) {
                itemDailyPurchaseLoggerMap.put(roleDailyBuyLogger.getItemId(), roleDailyBuyLogger);
            }
        }
        JSONObject response = new JSONObject();
        for (MarketItem item : items) {
            MarketItemDailyPurchaseLogger roleDailyBuyLogger = itemDailyPurchaseLoggerMap.get(item.getId());
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(item.getItemTemplateId());

            ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

            JSONObject dailyData = new JSONObject();
            dailyData.put("showDiscount", item.showDiscount(itemUnit));
            dailyData.put("showHasBuy", "您已购买" + (roleDailyBuyLogger == null ? 0 : roleDailyBuyLogger.getHasBuyCount()) + itemUnit.getTitle());
            dailyData.put("hasBuy", roleDailyBuyLogger == null ? 0 : roleDailyBuyLogger.getHasBuyCount());
            dailyData.put("beyondControl", item.getBeyondControl());

            response.put(String.valueOf(item.getId()), dailyData);
        }
        return response;
    }

    private JSONArray fillPageItemMessage(List<MarketItem> items) {
        JSONArray itemArray = new JSONArray();
        for (MarketItem item : items) {
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(item.getItemTemplateId());
            if (itemTemplate == null) {
                continue;
            }
            ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

            JSONObject itemMsg = new JSONObject();

            itemMsg.put("itemId", item.getId());
            itemMsg.put("itemTemplateId", item.getItemTemplateId());
            itemMsg.put("name", itemTemplate.getName());
            itemMsg.put("imageUrl", itemTemplate.getImageUrl());
            itemMsg.put("unitName", itemUnit == null ? "" : itemUnit.getTitle());
            itemMsg.put("stock", item.getShowStock());
            itemMsg.put("maximumSellCount", item.getMaximumSellCount());
            itemMsg.put("minimumSellCount", item.getMinimumSellCount());
            itemMsg.put("barcode", itemTemplate.getBarcode());
            itemMsg.put("batchesCode", CommonUtils.nullToEmpty(item.getBatchesCode()));
            itemMsg.put("sellPrice", item.getSellPrice());
            itemMsg.put("maxPrice", item.maxPrice());
            itemMsg.put("discountType", item.getDiscountType());
            itemMsg.put("discountPrice", (item.getDiscountType() == DiscountTypeEnum.NORMAL.getKey()) ? 0 : (item.getDiscountPrice() <= 0 ? item.getSellPrice() : item.getDiscountPrice()));
            itemMsg.put("restrictionQuantity", item.getRestrictionQuantity());
            itemMsg.put("actualSales", item.getActualSales());
            itemMsg.put("deliveryDelay", item.getDeliveryDelay());
            itemMsg.put("status", item.getStatus());

            itemArray.add(itemMsg);
        }
        return itemArray;
    }

    private String itemSet(List<MarketItem> items) {
        StringBuilder itemSet = new StringBuilder();
        for (MarketItem item : items) {
            itemSet.append(item.getItemTemplateId()).append(CommonUtils.SPLIT_COMMA);
        }
        itemSet.deleteCharAt(itemSet.length() - 1);

        return itemSet.toString();
    }
}