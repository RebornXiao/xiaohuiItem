package com.xlibao.saas.market.service.market.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.exception.PlatformErrorCodeEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.exception.code.ItemErrorCodeEnum;
import com.xlibao.common.support.PassportRemoteService;
import com.xlibao.datacache.item.ItemDataCacheService;
import com.xlibao.market.data.model.*;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.metadata.passport.Passport;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.item.ItemStockOffsetTypeEnum;
import com.xlibao.saas.market.service.item.MarketItemErrorCodeEnum;
import com.xlibao.saas.market.service.item.MarketItemStatusEnum;
import com.xlibao.saas.market.service.item.PrepareActionStatusEnum;
import com.xlibao.saas.market.service.market.*;
import com.xlibao.saas.market.service.support.remote.MarketShopRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chinahuangxc on 2017/8/16.
 */
@Transactional
@Service("shelvesService")
public class ShelvesServiceImpl extends BasicWebService implements ShelvesService {

    private static final Logger logger = LoggerFactory.getLogger(ShelvesServiceImpl.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;
    @Autowired
    private MarketShopRemoteService marketShopRemoteService;

    private static String VALID_ACTION_STATUS_SET = PrepareActionStatusEnum.UN_EXECUTOR.getKey() + CommonUtils.SPLIT_COMMA + PrepareActionStatusEnum.EXCEPTION.getKey();

    @Override
    public void builderShelvesData(MarketEntry marketEntry, String content) {
        String[] contentArray = content.split("\r\n");
        for (String c : contentArray) {
            String dataType = c.substring(0, 4);
            String data = c.substring(4).replaceAll("\\[", "").replaceAll("]", "");
            if ("0000".equals(dataType)) { // 反馈整个店铺有多少个组，每组有多少个单元的信息
                // 清空原来的货架数据
                dataAccessFactory.getMarketDataAccessManager().clearShelves(marketEntry.getId());
                groupAdapter(marketEntry, data);
                continue;
            }
            unitAdapter(marketEntry, dataType, data);
        }
    }

    /**
     * <pre>
     *     <b>获取组(走道)、货架(单元)、层(楼层)的值</b>
     *
     *     <b>访问地址：</b>http://domainName/market/open/getShelvesMarks.do
     *     <b>访问方式：</b>GET/POST 推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>marketId</b> - long 商店ID，必填参数。
     *          <b>groupCode</b> - String 组编码，当 <b>shelvesType</b>为{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#GROUP}或{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#UNIT}时必填
     *          <b>unitCode</b> - String 单元编码，当 <b>shelvesType</b>为{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum#UNIT}时必填
     *          <b>shelvesType</b> - int 获取的货架类型，具体参考：{@link com.xlibao.saas.market.service.market.ShelvesTypeEnum}
     *
     *     <b>返回：</b>
     *          <b>datas</b> - JSONArray 编码组，每个元素为String，即对应请求的数据集合
     * </pre>
     */
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
            return MarketErrorCodeEnum.SHELVES_LOCATION_ERROR.response("货架信息有误，请检查是否未重置货架信息或提供的信息错误");
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
        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionsForLocationSet(marketId, locationSet.toString(), VALID_ACTION_STATUS_SET);
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
            data.put("taskStatus", prepareAction == null ? 0 : prepareAction.getStatus());
            data.put("hasCompleteQuantity", prepareAction == null ? 0 : prepareAction.getHasCompleteQuantity());
            data.put("hopeItemQuantity", prepareAction == null ? 0 : prepareAction.getHopeItemQuantity());

            response.add(data);
        }
        // TODO 将当前分页，当前页内容数量都要发回去
        return success(response);
    }

    @Override
    public JSONObject scanShelvesTask() {
        long marketId = getLongParameter("marketId");
        String barcode = getUTF("barcode");

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionForBarcode(marketId, barcode, VALID_ACTION_STATUS_SET);
        if (CommonUtils.isEmpty(prepareActions)) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.response("商品[条码：" + barcode + "]不存在预存任务");
        }
        MarketPrepareAction prepareAction = prepareActions.get(0);
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareAction.getHopeItemTemplateId());
        ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

        JSONObject response = new JSONObject();

        response.put("taskId", prepareAction.getId());
        response.put("locationCode", prepareAction.getItemLocation());
        response.put("itemName", itemTemplate.getName());
        response.put("unitName", itemUnit.getTitle());
        response.put("hasCompleteQuantity", prepareAction.getHasCompleteQuantity());
        response.put("itemQuantity", prepareAction.getHopeItemQuantity());
        response.put("barcode", barcode);
        response.put("status", prepareAction.getStatus());

        return success(response);
    }

    @Override
    public JSONObject prepareAction() {
        long passportId = getLongParameter("passportId");
        long marketId = getLongParameter("marketId");
        String actionDatas = getUTF("actionDatas"); // 结构：[{"location":"01010101","itemTemplateId":"100000","quantity":"9"}, {"location":"01010102","itemTemplateId":"100000","quantity":"10"}, {"location":"01010103","itemTemplateId":"100001","quantity":"11"}]
        String hopeExecutorDate = getUTF("hopeExecutorDate", CommonUtils.dateFormat(CommonUtils.getTodayEarlyMorningMillisecond()));

        JSONArray actionArray = JSONObject.parseArray(actionDatas);

        beforePrepareAction(passportId, marketId, actionArray, hopeExecutorDate); // 执行前的检查 检查本次的数量与原位置上是否存在未完成的任务

        for (int i = 0; i < actionArray.size(); i++) {
            JSONObject data = actionArray.getJSONObject(i);

            createPrepareAction(passportId, marketId, hopeExecutorDate, data, ShelvesTaskTypeEnum.ON_SHELVES);
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

        return unExecutorTask(marketId, pageStartIndex, pageSize);
    }

    @Override
    public JSONObject cancelPrepareActionTask() {
        long taskId = getLongParameter("taskId");

        MarketPrepareAction prepareAction = dataAccessFactory.getItemDataAccessManager().getPrepareAction(taskId);
        if (prepareAction == null) {
            return MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.response("找不到任务，任务ID：" + taskId);
        }
        int result = dataAccessFactory.getItemDataAccessManager().modifyPrepareActionStatus(prepareAction.getMarketId(), prepareAction.getItemLocation(), VALID_ACTION_STATUS_SET, PrepareActionStatusEnum.INVALID.getKey(), CommonUtils.nowFormat());
        return result <= 0 ? fail() : success();
    }

    @Override
    public JSONObject showShelvesTask() {
        long marketId = getLongParameter("marketId");
        long passportId = getLongParameter("passportId");
        int status = getIntParameter("status", GlobalAppointmentOptEnum.LOGIC_FALSE.getKey());
        int pageSize = getPageSize();
        int pageStartIndex = getPageStartIndex(pageSize);
        if (status == GlobalAppointmentOptEnum.LOGIC_FALSE.getKey()) {
            // 未完成的列表
            MarketRelationship marketRelationship = dataAccessFactory.getMarketDataAccessManager().getRelationship(passportId, marketId, MarketRelationshipTypeEnum.FOCUS.getKey());
            if (marketRelationship == null) {
                return MarketErrorCodeEnum.CAN_NOT_FOUND_FOCUS_RELATIONSHIP.response("您没有权限查看该商店的任务，请联系管理员！");
            }
            return unExecutorTask(marketId, pageStartIndex, pageSize);
        }
        return hasExecutorTask(passportId, marketId, pageStartIndex, pageSize);
    }

    @Override
    public JSONObject offShelves() {
        // 主要用于记录操作日志
        long passportId = getLongParameter("passportId");
        long marketId = getLongParameter("marketId");
        String location = getUTF("location");
        String barcode = getUTF("barcode");
        int offShelvesQuantity = getIntParameter("offShelvesQuantity");
        if (offShelvesQuantity <= 0) {
            return PlatformErrorCodeEnum.ILLEGAL_ARGUMENT.response("下架数量必须大于0");
        }
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);
        if (marketEntry.getStatus() != MarketStatusEnum.MAINTAIN.getKey()) {
            logger.error("[下架] " + passportId + "没有先将商店[" + marketEntry.getName() + "]进行维护操作便执行了下架操作，系统已拦截该请求！");
            return MarketErrorCodeEnum.DON_NOT_MAINTAIN.response("商店[" + marketEntry.getName() + "]必须处于维护中才能执行该操作");
        }
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplateForBarcode(barcode);
        if (itemTemplate == null) {
            throw ItemErrorCodeEnum.BARCODE_NOT_EXIST.throwException("不存在条码为[" + barcode + "]的商品");
        }
        // 获取下架任务记录(包括未执行或执行一半的记录)
        MarketPrepareAction prepareAction = dataAccessFactory.getItemDataAccessManager().getPrepareAction(marketId, location, ShelvesTaskTypeEnum.OFF_SHELVES.getKey(), VALID_ACTION_STATUS_SET);
        if (prepareAction == null) {
            return MarketItemErrorCodeEnum.NOT_FOUND_PREPARE_ACTION.response("位置" + location + "不存在预下架任务");
        }
        int hopeExecutorQuantity = (prepareAction.getHopeItemQuantity() - prepareAction.getHasCompleteQuantity());
        if (offShelvesQuantity > hopeExecutorQuantity) {
            return MarketItemErrorCodeEnum.ITEM_LOCATION_QUANTITY_ERROR.response("错误的操作行为，您本次执行的下架数量：" + offShelvesQuantity + "；任务要求下架：" + hopeExecutorQuantity);
        }
        MarketItem item = dataAccessFactory.getItemDataAccessManager().getItem(marketId, itemTemplate.getId());
        if (item == null) {
            return MarketItemErrorCodeEnum.NOT_FOUND_ITEM.response("商店不存在条码为[" + barcode + "]的商品");
        }
        MarketItemLocation itemLocation = dataAccessFactory.getItemDataAccessManager().getItemLocation(item.getId(), location);
        if (itemLocation == null) {
            return MarketItemErrorCodeEnum.ITEM_LOCATION_ERROR.response("位置[" + location + "]上不存在条码为[" + barcode + "]的商品");
        }
        if (itemLocation.getStock() < offShelvesQuantity) {
            return MarketItemErrorCodeEnum.ITEM_LOCATION_QUANTITY_ERROR.response("[0001]下架数量有误；货架商品剩余数量：" + itemLocation.getStock() + "；本次下架数量：" + offShelvesQuantity);
        }
        if (item.getStock() < offShelvesQuantity) {
            return MarketItemErrorCodeEnum.ITEM_LOCATION_QUANTITY_ERROR.response("[0002]下架数量有误；商品剩余总数量：" + item.getStock() + "；本次下架数量：" + offShelvesQuantity);
        }
        int status = item.getStatus();
        if (item.getStock() <= offShelvesQuantity) {
            status = MarketItemStatusEnum.OFF_SALE.getKey();
        }
        Passport passport = PassportRemoteService.getPassport(passportId);
        logger.info("[下架] " + passport.getShowName() + "(" + passportId + ")正在对商品\"" + itemTemplate.getName() + "(条码为：" + barcode + ")\"" + "进行下架操作；商店ID：" + marketId + "，商品ID：" + item.getId()
                + "库存数量：" + item.getStock() + "，所在位置：" + location + "，位置上商品数量：" + itemLocation.getStock() + "，下架数量：" + offShelvesQuantity + "；下架后商品状态为：" + status);
        int result = dataAccessFactory.getItemDataAccessManager().offShelves(item.getId(), offShelvesQuantity, status); // 减少库存 当库存为0时 设置为下架
        if (result <= 0) {
            throw PlatformErrorCodeEnum.DB_ERROR.throwException("[0001]更新商品库存失败");
        }
        // 需减少位置上的数量
        result = dataAccessFactory.getItemDataAccessManager().offsetItemLocationStock(itemLocation, offShelvesQuantity, ItemStockOffsetTypeEnum.OFF_SHELVES.getKey(), passportId, passport.getShowName());
        if (result <= 0) {
            throw PlatformErrorCodeEnum.DB_ERROR.throwException("[0002]更新商品库存失败");
        }
        int taskStatus = PrepareActionStatusEnum.COMPLETE.getKey();
        String mark = "[系统]正常完成下架任务";
        if (offShelvesQuantity != hopeExecutorQuantity) {
            mark = "[系统]任务要求下架数量：" + hopeExecutorQuantity + "；由于货架上商品数量剩余" + itemLocation.getStock() + "；本次下架数量：" + offShelvesQuantity + "属于正常操作";
            if (offShelvesQuantity != itemLocation.getStock()) {
                taskStatus = PrepareActionStatusEnum.EXCEPTION.getKey();
                mark = "[系统]任务要求下架数量：" + hopeExecutorQuantity + "；但本次执行下架数量为：" + offShelvesQuantity + "；货架上商品剩余数量为：" + itemLocation.getStock();
            }
        }
        completePrepareActionTask(marketId, location, ShelvesTaskTypeEnum.OFF_SHELVES.getKey(), passportId, itemTemplate.getId(), offShelvesQuantity, hopeExecutorQuantity, mark, prepareAction.getStatus(), taskStatus);
        return success();
    }

    @Override
    public JSONObject onShelves() {
        // 主要用于记录操作日志
        long passportId = getLongParameter("passportId");
        long marketId = getLongParameter("marketId");
        String location = getUTF("location");
        String barcode = getUTF("barcode");
        int onShelvesQuantity = getIntParameter("onShelvesQuantity");

        if (onShelvesQuantity <= 0) {
            return PlatformErrorCodeEnum.ILLEGAL_ARGUMENT.response("上架数量必须大于0");
        }
        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarket(marketId);
        if (marketEntry.getStatus() != MarketStatusEnum.MAINTAIN.getKey()) {
            logger.error("[上架] " + passportId + "没有先将商店[" + marketEntry.getName() + "]进行维护操作便执行了上架操作，系统已拦截该请求！");
            return MarketErrorCodeEnum.DON_NOT_MAINTAIN.response("商店[" + marketEntry.getName() + "]必须处于维护中才能执行该操作");
        }
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplateForBarcode(barcode);
        if (itemTemplate == null) {
            throw ItemErrorCodeEnum.BARCODE_NOT_EXIST.throwException("不存在条码为[" + barcode + "]的商品");
        }
        MarketPrepareAction prepareAction = dataAccessFactory.getItemDataAccessManager().getPrepareAction(marketId, location, ShelvesTaskTypeEnum.ON_SHELVES.getKey(), VALID_ACTION_STATUS_SET);
        if (prepareAction == null) {
            return MarketItemErrorCodeEnum.NOT_FOUND_PREPARE_ACTION.response("位置" + location + "不存在上架任务");
        }
        if (!barcode.equals(prepareAction.getHopeItemBarcode())) {
            ItemTemplate it = ItemDataCacheService.getItemTemplateForBarcode(prepareAction.getHopeItemBarcode());
            return MarketItemErrorCodeEnum.ERROR_PREPARE_ACTION.response("操作被拒绝；期望该位置存放商品(条码：" + prepareAction.getHopeItemBarcode() + "，名称：[" + it.getName() + "])，" +
                    "与您提供的商品(条码：" + barcode + "，名称：[" + itemTemplate.getName() + "])不一致");
        }
        int hopeExecutorQuantity = (prepareAction.getHopeItemQuantity() - prepareAction.getHasCompleteQuantity());
        if (onShelvesQuantity > hopeExecutorQuantity) {
            return MarketItemErrorCodeEnum.ITEM_LOCATION_QUANTITY_ERROR.response("错误的操作行为，您本次执行的上架数量：" + onShelvesQuantity + "；任务要求上架：" + hopeExecutorQuantity);
        }
        MarketItem item = dataAccessFactory.getItemDataAccessManager().getItem(marketId, itemTemplate.getId());
        if (item == null) {
            // 商店未存在该模版的商品 先添加
            item = MarketItem.newInstance(marketId, itemTemplate, (byte) MarketItemStatusEnum.NORMAL.getKey());
            dataAccessFactory.getItemDataAccessManager().createItem(item);
        }
        Passport passport = PassportRemoteService.getPassport(passportId);
        logger.info("[上架] " + passport.getShowName() + "(" + passportId + ")正在对商品\"" + itemTemplate.getName() + "(条码为：" + barcode + ")\"" + "进行上架操作；商店ID：" + marketId + "，商品ID：" + item.getId()
                + "，所在位置：" + location + "，上架数量：" + onShelvesQuantity);
        // 存在该模版的商品时 更新库存和状态即可 注意：使用了下架的数据库支持，因此偏移值必须为负数
        dataAccessFactory.getItemDataAccessManager().offShelves(item.getId(), -onShelvesQuantity, MarketItemStatusEnum.NORMAL.getKey());

        int taskStatus = PrepareActionStatusEnum.COMPLETE.getKey();
        String mark = "[系统]正常完成上架任务";
        if (onShelvesQuantity < hopeExecutorQuantity) {
            taskStatus = PrepareActionStatusEnum.EXCEPTION.getKey();
            mark = "[系统]任务要求上架架数量：" + hopeExecutorQuantity + "；但本次执行上架数量为：" + onShelvesQuantity + "；未完成数量为：" + (hopeExecutorQuantity - onShelvesQuantity);
        }

        MarketItemLocation itemLocation = dataAccessFactory.getItemDataAccessManager().getItemLocationForMarket(marketId, location);
        if (itemLocation != null) {
            if (itemLocation.getStock() > 0) {
                if (!Objects.equals(itemLocation.getItemId(), item.getId())) {
                    itemTemplate = ItemDataCacheService.getItemTemplate(item.getItemTemplateId());
                    throw MarketItemErrorCodeEnum.ITEM_LOCATION_ERROR.throwException("位置[" + location + "]上存在条码为[" + itemTemplate.getBarcode() + "]的商品[" + itemTemplate.getName() + "]");
                }
                // 需增加位置上的数量
                dataAccessFactory.getItemDataAccessManager().offsetItemLocationStock(itemLocation, -onShelvesQuantity, ItemStockOffsetTypeEnum.ON_SHELVES.getKey(), passportId, passport.getShowName());
                // 完成了预操作行为
                completePrepareActionTask(marketId, location, ShelvesTaskTypeEnum.ON_SHELVES.getKey(), passportId, itemTemplate.getId(), onShelvesQuantity, prepareAction.getHopeItemQuantity(), mark, prepareAction.getStatus(), taskStatus);
                return success();
            }
            // 直接移除原来位置的商品信息
            dataAccessFactory.getItemDataAccessManager().removeItemLocation(itemLocation.getId());
        }
        itemLocation = MarketItemLocation.newInstance(marketId, item.getId(), item.getItemTemplateId(), location, onShelvesQuantity);
        // 新建位置信息
        dataAccessFactory.getItemDataAccessManager().createItemLocation(itemLocation, ShelvesTaskTypeEnum.ON_SHELVES.getKey(), passportId, passport.getShowName());
        // 完成了预操作行为
        completePrepareActionTask(marketId, location, ShelvesTaskTypeEnum.ON_SHELVES.getKey(), passportId, itemTemplate.getId(), onShelvesQuantity, prepareAction.getHopeItemQuantity(), mark, prepareAction.getStatus(), taskStatus);
        return success();
    }

    private void completePrepareActionTask(long marketId, String location, int type, long executorPassportId, long itemTemplateId, int quantity, int hopeQuantity, String mark, int matchActionStatus, int hopeActionStatus) {
        // 完成了预操作行为
        dataAccessFactory.getItemDataAccessManager().modifyPrepareActionStatus(marketId, location, String.valueOf(matchActionStatus), hopeActionStatus, CommonUtils.nowFormat());

        MarketTaskLogger taskLogger = new MarketTaskLogger();
        taskLogger.setMarketId(marketId);
        taskLogger.setItemLocation(location);
        taskLogger.setType(type);
        taskLogger.setExecutorPassportId(executorPassportId);
        taskLogger.setItemTemplateId(itemTemplateId);
        taskLogger.setItemQuantity(quantity);
        taskLogger.setHopeItemQuantity(hopeQuantity);
        taskLogger.setMark(mark);
        dataAccessFactory.getMarketDataAccessManager().createTaskLogger(taskLogger);
    }

    private JSONObject unExecutorTask(long marketId, int pageStartIndex, int pageSize) {
        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getUnCompletePrepareActions(marketId, pageStartIndex, pageSize);

        if (CommonUtils.isEmpty(prepareActions)) {
            throw MarketErrorCodeEnum.SHELVES_LOCATION_TASK_ERROR.throwException("没有存在未执行的任务");
        }
        JSONArray response = prepareActions.stream().map(this::fillPrepareActionMsg).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    private JSONObject hasExecutorTask(long passportId, long marketId, int pageStartIndex, int pageSize) {
        List<MarketTaskLogger> taskLoggers = dataAccessFactory.getMarketDataAccessManager().getTaskLoggers(passportId, marketId, pageStartIndex, pageSize);
        if (CommonUtils.isEmpty(taskLoggers)) {
            return PlatformErrorCodeEnum.NO_MORE_DATA.response();
        }
        JSONArray response = taskLoggers.stream().map(this::fillTaskLoggerMsg).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    private JSONObject fillPrepareActionMsg(MarketPrepareAction prepareAction) {
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareAction.getHopeItemTemplateId());
        ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

        String formatDate = CommonUtils.defineDateFormat(prepareAction.getHopeExecutorDate().getTime(), CommonUtils.Y_M_D);

        JSONObject response = new JSONObject();
        response.put("taskId", prepareAction.getId());
        response.put("locationCode", prepareAction.getItemLocation());
        response.put("itemTemplateId", prepareAction.getHopeItemTemplateId());
        response.put("itemName", itemTemplate.getName());
        response.put("unitName", itemUnit.getTitle());
        response.put("hasCompleteQuantity", prepareAction.getHasCompleteQuantity());
        response.put("itemQuantity", prepareAction.getHopeItemQuantity());
        response.put("barcode", prepareAction.getHopeItemBarcode());
        response.put("status", prepareAction.getStatus());
        response.put("hopeExecutorDate", formatDate);

        if (CommonUtils.isToday(prepareAction.getHopeExecutorDate().getTime())) {
            response.put("dateTitle", "今日");
        } else if (CommonUtils.isSameDay(prepareAction.getHopeExecutorDate().getTime(), System.currentTimeMillis() - CommonUtils.DAY_MILLISECOND_TIME)) {
            response.put("dateTitle", "昨日");
        } else {
            response.put("dateTitle", formatDate + " " + CommonUtils.dayOfWeekForTime(prepareAction.getHopeExecutorDate().getTime()));
        }
        return response;
    }

    private JSONObject fillTaskLoggerMsg(MarketTaskLogger taskLogger) {
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(taskLogger.getItemTemplateId());
        ItemUnit itemUnit = ItemDataCacheService.getItemUnit(itemTemplate.getUnitId());

        String formatDate = CommonUtils.defineDateFormat(taskLogger.getCreateTime().getTime(), CommonUtils.Y_M_D);

        JSONObject response = new JSONObject();

        response.put("taskId", taskLogger.getId());
        response.put("locationCode", taskLogger.getItemLocation());
        response.put("itemTemplateId", taskLogger.getItemTemplateId());
        response.put("itemName", itemTemplate.getName());
        response.put("unitName", itemUnit.getTitle());
        response.put("itemQuantity", taskLogger.getItemQuantity());
        response.put("barcode", itemTemplate.getBarcode());
        response.put("executorDate", formatDate);

        if (CommonUtils.isToday(taskLogger.getCreateTime().getTime())) {
            response.put("dateTitle", "今日");
        } else if (CommonUtils.isSameDay(taskLogger.getCreateTime().getTime(), System.currentTimeMillis() - CommonUtils.DAY_MILLISECOND_TIME)) {
            response.put("dateTitle", "昨日");
        } else {
            response.put("dateTitle", formatDate + " " + CommonUtils.dayOfWeekForTime(taskLogger.getCreateTime().getTime()));
        }
        return response;
    }

    private void beforePrepareAction(long passportId, long marketId, JSONArray actionArray, String hopeExecutorDate) {
        StringBuilder locationSet = new StringBuilder();
        StringBuilder itemTemplateSet = new StringBuilder();
        for (int i = 0; i < actionArray.size(); i++) {
            JSONObject data = actionArray.getJSONObject(i);

            long itemTemplateId = data.getLongValue("itemTemplateId");
            ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(itemTemplateId);
            int quantity = data.getIntValue("quantity");
            if (quantity <= 0) {
                throw MarketItemErrorCodeEnum.PREPARE_QUANTITY_ERROR.throwException("[" + itemTemplate.getName() + "]预上架数量必须大于0");
            }
            locationSet.append("'").append(data.getString("location")).append("'").append(CommonUtils.SPLIT_COMMA);
            itemTemplateSet.append(itemTemplateId).append(CommonUtils.SPLIT_COMMA);
        }
        locationSet.deleteCharAt(locationSet.length() - 1);
        itemTemplateSet.deleteCharAt(itemTemplateSet.length() - 1);

        List<MarketPrepareAction> prepareActions = dataAccessFactory.getItemDataAccessManager().getPrepareActionsForLocationSet(marketId, locationSet.toString(), VALID_ACTION_STATUS_SET);
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
        // 建立下架任务
        List<MarketItemLocation> itemLocations = dataAccessFactory.getItemDataAccessManager().getItemLocationsForMarket(marketId, locationSet.toString());
        for (MarketItemLocation itemLocation : itemLocations) {
            JSONObject prepareActionData = new JSONObject();
            prepareActionData.put("itemTemplateId", itemLocation.getItemTemplateId());
            prepareActionData.put("location", itemLocation.getLocationCode());
            prepareActionData.put("quantity", itemLocation.getStock()); // 该数量并不能作为最终控制的数量，仅为期望或参考数据

            createPrepareAction(passportId, marketId, hopeExecutorDate, prepareActionData, ShelvesTaskTypeEnum.OFF_SHELVES);
        }
    }

    private void createPrepareAction(long passportId, long marketId, String hopeExecutorDate, JSONObject prepareActionData, ShelvesTaskTypeEnum shelvesTaskTypeEnum) {
        ItemTemplate itemTemplate = ItemDataCacheService.getItemTemplate(prepareActionData.getLongValue("itemTemplateId"));

        MarketPrepareAction prepareAction = new MarketPrepareAction();
        prepareAction.setMarketId(marketId);
        prepareAction.setActionPassportId(passportId);
        prepareAction.setItemLocation(prepareActionData.getString("location"));
        prepareAction.setHopeItemTemplateId(itemTemplate.getId());
        prepareAction.setHopeItemBarcode(itemTemplate.getBarcode());
        prepareAction.setHasCompleteQuantity(0);
        prepareAction.setHopeItemQuantity(prepareActionData.getIntValue("quantity"));
        prepareAction.setHopeExecutorDate(new Date(CommonUtils.dateFormatToLong(hopeExecutorDate)));
        prepareAction.setType(shelvesTaskTypeEnum.getKey());
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
        marketShopRemoteService.shelvesMessage(marketEntry.getPassportId(), groupCode);
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