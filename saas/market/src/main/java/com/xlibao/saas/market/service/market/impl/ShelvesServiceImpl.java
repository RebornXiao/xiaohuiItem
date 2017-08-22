package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.data.model.MarketItemLocation;
import com.xlibao.market.data.model.MarketPrepareAction;
import com.xlibao.market.data.model.MarketShelvesManager;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.item.PrepareActionStatusEnum;
import com.xlibao.saas.market.service.market.MarketStatusEnum;
import com.xlibao.saas.market.service.market.ShelvesService;
import com.xlibao.saas.market.service.market.ShelvesTypeEnum;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/16.
 */
@Transactional
@Service("shelvesService")
public class ShelvesServiceImpl extends BasicWebService implements ShelvesService {

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

    @Override
    public void builderShelvesData(MarketEntry marketEntry, String content) {
        if (marketEntry.getStatus() != MarketStatusEnum.INITIALIZATION.getKey()) {
            // TODO 不处于初始化过程
        }
        String[] contentArray = content.split("\r\n");
        for (String c : contentArray) {
            String dataType = c.substring(0, 4);
            String data = c.substring(4).replaceAll("\\[", "").replaceAll("]", "");
            if ("0000".equals(dataType)) {
                groupAdapter(marketEntry, data);
                continue;
            }
            unitAdapter(marketEntry, dataType, data);
        }
    }

    @Override
    public JSONObject getShelvesMarks() {
        long marketId = getLongParameter("marketId");
        String groupCode = getUTF("groupCode", "");
        String unitCode = getUTF("unitCode", "");
        int shelvesType = getIntParameter("shelvesType", ShelvesTypeEnum.MARKET.getKey());

        List<String> marks = dataAccessFactory.getMarketDataAccessManager().getShelvesMarks(marketId, groupCode, unitCode, shelvesType);

        return success(marks);
    }

    @Override
    public JSONObject loaderClipDatas() {
        long marketId = getLongParameter("marketId");
        String groupCode = getUTF("groupCode");
        String unitCode = getUTF("unitCode");
        String floorCode = getUTF("floorCode");
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex(pageSize);

        List<MarketItemLocation> itemLocations = dataAccessFactory.getItemDataAccessManager().matchItemLocationForMarket(marketId, groupCode + unitCode + floorCode, pageStartIndex, pageSize);
        StringBuilder locationSet = new StringBuilder();
        for (MarketItemLocation itemLocation : itemLocations) {
            locationSet.append(itemLocation.getLocationCode()).append(CommonUtils.SPLIT_COMMA);
        }
        locationSet.deleteCharAt(locationSet.length() - 1);
        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionsForLocationSet(marketId, locationSet.toString(), PrepareActionStatusEnum.UN_EXECUTOR.getKey());
        Map<String, MarketPrepareAction> prepareActionMap = new HashMap<>();
        if (!CommonUtils.isEmpty(prepareActions)) {
            for (MarketPrepareAction prepareAction : prepareActions) {
                prepareActionMap.put(prepareAction.getItemLocation(), prepareAction);
            }
        }

        JSONArray response = new JSONArray();
        for (MarketItemLocation itemLocation : itemLocations) {
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemLocation.getItemTemplateId());
            ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

            JSONObject data = new JSONObject();
            data.put("locationCode", itemLocation.getLocationCode());
            data.put("barcode", itemTemplate.getBarcode());
            data.put("name", itemTemplate.getName());
            data.put("unitName", itemUnit.getTitle());
            data.put("stock", itemLocation.getStock());

            MarketPrepareAction prepareAction = prepareActionMap.get(itemLocation.getLocationCode());
            data.put("taskId", prepareAction == null ? 0 : prepareAction.getId());

            response.add(data);
        }
        return success(response);
    }

    private void groupAdapter(MarketEntry marketEntry, String content) {
        String[] groups = content.split(CommonUtils.SPLIT_COMMA);
        for (int i = 0; i < groups.length; i++) {
            String groupCode = CommonUtils.numberToString(String.valueOf(i + 1), 2, "0"); // 当前组的编码

            int unitQuantity = Integer.parseInt(groups[i]); // 当前组的单元数量
            for (int j = 0; j < unitQuantity; j++) {
                String unitCode = CommonUtils.numberToString(String.valueOf(j + 1), 2, "0"); // 当前单元的编码

                createShelves(marketEntry.getId(), marketEntry.getPassportId(), groupCode, unitCode, "01", "01");
            }
            // 刷新单元信息
            refreshUnitDatas(marketEntry, groupCode);
        }
    }

    private void unitAdapter(MarketEntry marketEntry, String dataType, String content) {
        String[] floors = content.split(CommonUtils.SPLIT_COMMA);
        for (int i = 0; i < floors.length; i++) {
            String floorCode = CommonUtils.numberToString(String.valueOf(i + 1), 2, "0"); // 当前楼层的编码

            int clipQuantity = Integer.parseInt(floors[i]); // 当前楼层的弹夹个数
            for (int j = 0; j < clipQuantity; j++) {
                String clipCode = CommonUtils.numberToString(String.valueOf(j + 1), 2, "0");

                if ("01".equals(floorCode) && "01".equals(clipCode)) { // 已初始化
                    continue;
                }
                createShelves(marketEntry.getId(), marketEntry.getPassportId(), dataType.substring(0, 2), dataType.substring(2), floorCode, clipCode);
            }
        }
    }

    private void refreshUnitDatas(MarketEntry marketEntry, String groupCode) {
        String content = HardwareMessageType.SHELVES + groupCode;

        marketShopRemoteService.shelvesMessage(marketEntry.getPassportId(), content);
    }

    private void createShelves(long marketId, long passportId, String groupCode, String unitCode, String floorCode, String clipCode) {
        MarketShelvesManager shelvesManager = new MarketShelvesManager();
        shelvesManager.setMarketId(marketId);
        shelvesManager.setGroupCode(groupCode);
        shelvesManager.setUnitCode(unitCode);
        shelvesManager.setFloorCode(floorCode);
        shelvesManager.setClipCode(clipCode);
        shelvesManager.setCreateTime(new Date());
        shelvesManager.setModifyPassportId(passportId);
        shelvesManager.setLastModifyTime(new Date());
        dataAccessFactory.getMarketDataAccessManager().createShelves(shelvesManager);
    }
}