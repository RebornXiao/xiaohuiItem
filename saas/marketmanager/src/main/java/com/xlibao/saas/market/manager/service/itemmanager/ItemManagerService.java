package com.xlibao.saas.market.manager.service.itemmanager;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.metadata.item.ItemType;
import com.xlibao.metadata.item.ItemUnit;

import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface ItemManagerService {

    public JSONObject searchItemUnitPageByName(String searchKey, int pageSize, int pageIndex);

    public JSONObject searchItemTypePageByName(String searchKey, int pageSize, int pageIndex);

    public JSONObject searchItemTypePage(long parentItemTypeId, int pageSize, int pageIndex);

    public JSONObject searchItemTemplatesPage(String searchType, String searchKey, int pageSize, int pageIndex);

    public Map<Long, ItemType> itemTypesToMap(List<ItemType> itemTypes);

    public List<ItemType> getItemTypes(int parentItemTypeId);

    public List<ItemUnit> getItemUnits();

    public Map<Long, ItemUnit> itemUnitsToMap(List<ItemUnit> itemUnits);

}
