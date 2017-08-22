package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.data.model.MarketItemLocation;
import com.xlibao.market.data.model.MarketPrepareAction;
import com.xlibao.market.data.model.MarketShelvesManager;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.item.MarketItemErrorCodeEnum;
import com.xlibao.saas.market.service.item.PrepareActionStatusEnum;
import com.xlibao.saas.market.service.market.MarketErrorCodeEnum;
import com.xlibao.saas.market.service.market.MarketStatusEnum;
import com.xlibao.saas.market.service.market.ShelvesService;
import com.xlibao.saas.market.service.market.ShelvesTypeEnum;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<MarketShelvesManager> shelvesManagers = dataAccessFactory.getMarketDataAccessManager().getClipDatas(marketId, groupCode, unitCode, floorCode, pageStartIndex, pageSize);
        if (CommonUtils.isEmpty(shelvesManagers)) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_ERROR.response("货架信息有误，从检查是否未重置货架信息或提供的信息错误");
        }
        StringBuilder locationSet = new StringBuilder();
        for (MarketShelvesManager shelvesManager : shelvesManagers) {
            locationSet.append("'").append(groupCode).append(unitCode).append(floorCode).append(shelvesManager.getClipCode()).append("'").append(CommonUtils.SPLIT_COMMA);
        }
        locationSet.deleteCharAt(locationSet.length() - 1);

        List<MarketItemLocation> itemLocations = dataAccessFactory.getItemDataAccessManager().matchItemLocationForMarket(marketId, groupCode + unitCode + floorCode, pageStartIndex, pageSize);
        Map<String, MarketItemLocation> itemLocationMap = new HashMap<>();
        for (MarketItemLocation itemLocation : itemLocations) {
            itemLocationMap.put(itemLocation.getLocationCode(), itemLocation);
        }

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionsForLocationSet(marketId, locationSet.toString(), PrepareActionStatusEnum.UN_EXECUTOR.getKey());
        Map<String, MarketPrepareAction> prepareActionMap = new HashMap<>();
        if (!CommonUtils.isEmpty(prepareActions)) {
            for (MarketPrepareAction prepareAction : prepareActions) {
                prepareActionMap.put(prepareAction.getItemLocation(), prepareAction);
            }
        }

        JSONArray response = new JSONArray();
        for (MarketShelvesManager shelvesManager : shelvesManagers) {
            String locationCode = groupCode + unitCode + floorCode + shelvesManager.getClipCode();
            MarketItemLocation itemLocation = itemLocationMap.get(locationCode);

            ItemTemplate itemTemplate = itemLocation == null ? null : ItemDataCacheService.getItemTemplate(itemLocation.getItemTemplateId());
            ItemUnit itemUnit = itemTemplate == null ? null : ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

            JSONObject data = new JSONObject();
            data.put("locationCode", locationCode);
            data.put("barcode", itemTemplate == null ? "未存放商品" : itemTemplate.getBarcode());
            data.put("itemTemplateId", itemTemplate == null ? 0 : itemTemplate.getId());
            data.put("itemName", itemTemplate == null ? "未存放商品" : itemTemplate.getName());
            data.put("unitName", itemUnit == null ? "未知单位" : itemUnit.getTitle());
            data.put("itemQuantity", itemLocation == null ? 0 : itemLocation.getStock());

            MarketPrepareAction prepareAction = prepareActionMap.get(locationCode);
            data.put("taskId", prepareAction == null ? 0 : prepareAction.getId());

            response.add(data);
        }
        return success(response);
    }

    @Override
    public JSONObject scanShelvesTask() {
        long marketId = getLongParameter("marketId");
        String barcode = getUTF("barcode");

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionForBarcode(marketId, barcode, PrepareActionStatusEnum.UN_EXECUTOR.getKey());
        if (CommonUtils.isEmpty(prepareActions)) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.response();
        }
        JSONArray response = new JSONArray();
        for (MarketPrepareAction prepareAction : prepareActions) {
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareAction.getHopeItemTemplateId());
            ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

            JSONObject data = new JSONObject();

            data.put("taskId", prepareAction.getId());
            data.put("itemName", itemTemplate.getName());
            data.put("locationCode", prepareAction.getItemLocation());
            data.put("itemQuantity", prepareAction.getHopeItemQuantity());
            data.put("unitName", itemUnit.getTitle());

            response.add(data);
        }
        return success(response);
    }

    @Override
    public JSONObject prepareAction() {
        long passportId = getLongParameter("passportId");
        long marketId = getLongParameter("marketId");
        String actionDatas = getUTF("actionDatas"); // 结构：[{"location":"01010101","itemTemplateId":"100000","quantity":"9"}, {"location":"01010102","itemTemplateId":"100000","quantity":"10"}, {"location":"01010103","itemTemplateId":"100001","quantity":"11"}]
        String hopeExecutorDate = getUTF("hopeExecutorDate", CommonUtils.dateFormat(CommonUtils.getTodayEarlyMorningMillisecond()));

        JSONArray actionArray = JSONObject.parseArray(actionDatas);

        beforePrepareAction(marketId, actionArray); // 执行前的检查 检查本次的数量与原位置上是否存在未完成的任务

        for (int i = 0; i < actionArray.size(); i++) {
            JSONObject data = actionArray.getJSONObject(i);

            createPrepareAction(passportId, marketId, hopeExecutorDate, data);
        }
        return success();
    }

    @Override
    public JSONObject checkPrepareActionTask() {
        long taskId = getLongParameter("taskId");
        MarketPrepareAction prepareAction = dataAccessFactory.getItemDataAccessManager().getPrepareAction(taskId);

        if (prepareAction == null) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.response("没有找到指定的任务，任务ID：" + taskId);
        }
        return success(fillPrepareActionMsg(prepareAction));
    }

    @Override
    public JSONObject unExecutorTask() {
        long marketId = getLongParameter("marketId");
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex(pageSize);

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getUnCompletePrepareActions(marketId, pageStartIndex, pageSize);

        if (CommonUtils.isEmpty(prepareActions)) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.response("没有存在未执行的任务");
        }
        JSONArray response = prepareActions.stream().map(this::fillPrepareActionMsg).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    private JSONObject fillPrepareActionMsg(MarketPrepareAction prepareAction) {
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareAction.getHopeItemTemplateId());
        ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

        JSONObject response = new JSONObject();
        response.put("taskId", prepareAction.getId());
        response.put("locationCode", prepareAction.getItemLocation());
        response.put("itemTemplateId", prepareAction.getHopeItemTemplateId());
        response.put("itemName", itemTemplate.getName());
        response.put("unitName", itemUnit.getTitle());
        response.put("itemQuantity", prepareAction.getHopeItemQuantity());
        response.put("barcode", prepareAction.getHopeItemBarcode());
        response.put("status", prepareAction.getStatus());
        response.put("hopeExecutorDate", CommonUtils.defineDateFormat(prepareAction.getHopeExecutorDate().getTime(), CommonUtils.Y_M_D));

        return response;
    }

    private void beforePrepareAction(long marketId, JSONArray actionArray) {
        StringBuilder locationSet = new StringBuilder();
        for (int i = 0; i < actionArray.size(); i++) {
            JSONObject data = actionArray.getJSONObject(i);

            long itemTemplateId = data.getLongValue("itemTemplateId");
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemTemplateId);
            int quantity = data.getIntValue("quantity");
            if (quantity <= 0) {
                throw MarketItemErrorCodeEnum.PREPARE_QUANTITY_ERROR.throwException("[" + itemTemplate.getName() + "]预上架数量必须大于0");
            }
            locationSet.append("'").append(data.getString("location")).append("'").append(CommonUtils.SPLIT_COMMA);
        }
        locationSet.deleteCharAt(locationSet.length() - 1);

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionsForLocationSet(marketId, locationSet.toString(), PrepareActionStatusEnum.UN_EXECUTOR.getKey());
        if (!CommonUtils.isEmpty(prepareActions)) { // 指定的位置 存在未执行的任务
            StringBuilder errorMsg = new StringBuilder().append("以下位置上存在未完成的任务：");
            for (MarketPrepareAction prepareAction : prepareActions) {
                ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareAction.getHopeItemTemplateId());
                ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());
                errorMsg.append("\r\n\t");
                errorMsg.append("位置 -- [").append(prepareAction.getItemLocation()).append("]期望存放").append(prepareAction.getHopeItemQuantity()).append(itemUnit.getTitle()).append("【").append(itemTemplate.getName()).append("】");
            }
            throw MarketItemErrorCodeEnum.PREPARE_ACTION_LOCATION_ERROR.throwException(errorMsg.toString());
        }
    }

    private void createPrepareAction(long passportId, long marketId, String hopeExecutorDate, JSONObject prepareActionData) {
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareActionData.getLongValue("itemTemplateId"));

        MarketPrepareAction prepareAction = new MarketPrepareAction();
        prepareAction.setMarketId(marketId);
        prepareAction.setActionPassportId(passportId);
        prepareAction.setItemLocation(prepareActionData.getString("location"));
        prepareAction.setHopeItemTemplateId(itemTemplate.getId());
        prepareAction.setHopeItemBarcode(itemTemplate.getBarcode());
        prepareAction.setHopeItemQuantity(prepareActionData.getIntValue("quantity"));
        prepareAction.setHopeExecutorDate(new Date(CommonUtils.dateFormatToLong(hopeExecutorDate)));
        prepareAction.setStatus(PrepareActionStatusEnum.UN_EXECUTOR.getKey());
        prepareAction.setCreateTime(new Date());

        int result = dataAccessFactory.getItemDataAccessManager().createPrepareAction(prepareAction);
        if (result <= 0) {
            throw new XlibaoRuntimeException("无法建立预操作记录，请稍后重试");
        }
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

    public static void main(String[] args) throws Exception {
        System.out.println(URLEncoder.encode("[{\"location\":\"01010101\",\"itemTemplateId\":\"100000\",\"quantity\":\"9\"}, {\"location\":\"01010102\",\"itemTemplateId\":\"100000\",\"quantity\":\"10\"}, {\"location\":\"01010103\",\"itemTemplateId\":\"100001\",\"quantity\":\"11\"}]", "UTF-8"));
    }
}