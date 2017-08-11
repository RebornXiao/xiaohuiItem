package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.common.http.HttpUtils;
import com.xlibao.metadata.item.ItemType;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.itemmanager.ItemManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/item")
public class ItemManagerController extends BaseController {

    @Autowired
    private ItemManagerService itemManagerService;

    @RequestMapping("/itemlist")
    public String getItemList(ModelMap map) {

        //取得商品列表
        String searchType = getUTF("searchType", null);
        String searchKey = getUTF("searchKey", null);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject itemJson = itemManagerService.searchItemTemplatesPage(searchType, searchKey, pageSize, pageIndex);

        if (itemJson.getIntValue("code") != 0) {
            return remoteFail(map, itemJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TEMPLATE);
        }

        //类型列表
        Map<Long, ItemType> itemTypeMap = itemManagerService.itemTypesToMap(itemManagerService.getItemTypes(-1));

        //单位列表
        Map<Long, ItemUnit> itemUnitMap = itemManagerService.itemUnitsToMap(itemManagerService.getItemUnits());

        JSONArray items = itemJson.getJSONObject("response").getJSONArray("data");
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            long type_id = item.getLong("typeId");//类型ID
            ItemType itemType = itemTypeMap.get(type_id);
            if (itemType != null) {
                item.put("typeName", itemType.getTitle());
            } else {
                item.put("typeName", "无");
            }

            long unit_id = item.getLong("unitId");//单位ID
            ItemUnit itemUnit = itemUnitMap.get(unit_id);
            if(itemUnit != null) {
                item.put("unitName", itemUnit.getTitle());
            } else {
                item.put("typeName", "无");
            }
        }

        map.put("itemlist", items);
        map.put("count", itemJson.getIntValue("count"));

        return jumpPage(map, LogicConfig.FTL_ITEMLIST, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TEMPLATE);
    }

    public String itemEdit(ModelMap map) {

        return LogicConfig.TAB_CHILD_NAME;
    }

    @RequestMapping("/itemtypes")
    public String getItemTypes(ModelMap map) {

        String searchKey = getUTF("searchKey", null);
        long parentItemTypeId = getLongParameter("parentItemTypeId", -1);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject typesJson = null;
        if(parentItemTypeId == -1) {
            //直接搜索
            typesJson = itemManagerService.searchItemTypePage(parentItemTypeId, pageSize, pageIndex);
        } else {
            //以分类获取
            typesJson = itemManagerService.searchItemTypePageByName(searchKey, pageSize, pageIndex);
        }

        if (typesJson.getIntValue("code") != 0) {
            return remoteFail(map, typesJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
        }

        JSONArray types = typesJson.getJSONObject("response").getJSONArray("data");

        map.put("itemTypes", types);
        map.put("count", typesJson.getIntValue("count"));

        return jumpPage(map, LogicConfig.FTL_ITEMLIST, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TYPE);
    }

    @RequestMapping("/itemunits")
    public String getItemUnit(ModelMap map) {

        String searchKey = getUTF("searchKey", null);
        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject unitJson = itemManagerService.searchItemUnitPageByName(searchKey, pageSize, pageIndex);

        if (unitJson.getIntValue("code") != 0) {
            return remoteFail(map, unitJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_UNIT);
        }

        JSONArray units = unitJson.getJSONObject("response").getJSONArray("data");

        map.put("itemUnits", units);
        map.put("count", unitJson.getIntValue("count"));

        return jumpPage(map, LogicConfig.FTL_ITEMLIST, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_UNIT);
    }
}