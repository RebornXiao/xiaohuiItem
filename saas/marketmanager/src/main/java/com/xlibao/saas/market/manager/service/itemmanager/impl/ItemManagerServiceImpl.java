package com.xlibao.saas.market.manager.service.itemmanager.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.http.HttpUtils;
import com.xlibao.metadata.item.ItemTemplate;
import com.xlibao.metadata.item.ItemType;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.itemmanager.ItemManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("itemManagerService")
public class ItemManagerServiceImpl implements ItemManagerService {

    public JSONObject searchItemUnitPageByName(String searchKey, int pageSize, int pageIndex) {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemUnitPageByName?searchKey="+searchKey+"&pageSize="+pageSize+"&pageIndex="+pageIndex);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

    public JSONObject searchItemTypePageByName(String searchKey, int pageSize, int pageIndex) {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemTypePageByName?searchKey="+searchKey+"&pageSize="+pageSize+"&pageIndex="+pageIndex);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

    public JSONObject searchItemTypePage(long parentItemTypeId, int pageSize, int pageIndex) {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemTypePage?parentItemTypeId="+parentItemTypeId+"&pageSize="+pageSize+"&pageIndex="+pageIndex);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

    public JSONObject searchItemTemplatesPage(String searchType, String searchKey, int pageSize, int pageIndex) {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemTemplatesPage?searchType="+searchType+"&searchKey="+searchKey+"&pageSize="+pageSize+"&pageIndex="+pageIndex);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

    @Override
    public Map<Long, ItemType> itemTypesToMap(List<ItemType> itemTypes) {
        if(itemTypes == null || itemTypes.size() < 1) {
            return new HashMap<>();
        }
        Map map = new HashMap();
        for (int i = 0; i < itemTypes.size(); i++) {
            ItemType it = itemTypes.get(i);
            map.put(it.getId(), it);
        }
        return map;
    }

    public List<ItemType> getItemTypes(int parentItemTypeId) {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemTypePage?parentItemTypeId=0");
        JSONObject response = JSONObject.parseObject(json);
        int code = response.getIntValue("code");
        if (code != 0) {
            throw new XlibaoRuntimeException(code, response.getString("msg"));
        }
        response = response.getJSONObject("response");
        JSONArray itemTypeArray = response.getJSONArray("data");

        List<ItemType> itemTypes = new ArrayList<>();

        for (int i = 0; i < itemTypeArray.size(); i++) {
            itemTypes.add(JSONObject.parseObject(itemTypeArray.getString(i), ItemType.class));
        }
        return itemTypes;
    }

    public List<ItemUnit> getItemUnits() {
        String json = HttpUtils.get(ConfigFactory.getDomainNameConfig().itemRemoteURL + "/item/searchItemUnitPageByName");
        JSONObject response = JSONObject.parseObject(json);
        int code = response.getIntValue("code");
        if (code != 0) {
            throw new XlibaoRuntimeException(code, response.getString("msg"));
        }
        response = response.getJSONObject("response");
        JSONArray itemUnitArray = response.getJSONArray("data");

        List<ItemUnit> itemUnits = new ArrayList<>();

        for (int i = 0; i < itemUnitArray.size(); i++) {
            itemUnits.add(JSONObject.parseObject(itemUnitArray.getString(i), ItemUnit.class));
        }
        return itemUnits;
    }

    @Override
    public Map<Long, ItemUnit> itemUnitsToMap(List<ItemUnit> itemUnits) {
        if(itemUnits == null || itemUnits.size() < 1) {
            return new HashMap<>();
        }
        Map map = new HashMap();
        for (int i = 0; i < itemUnits.size(); i++) {
            ItemUnit iu = itemUnits.get(i);
            map.put(iu.getId(), iu);
        }
        return map;
    }
}