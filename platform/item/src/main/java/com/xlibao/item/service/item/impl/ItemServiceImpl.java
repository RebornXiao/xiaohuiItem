package com.xlibao.item.service.item.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.GlobalConstantConfig;
import com.xlibao.common.constant.item.ItemStatusEnum;
import com.xlibao.item.data.mapper.ItemDataAccessManager;
import com.xlibao.item.service.item.ItemService;
import com.xlibao.metadata.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chinahuangxc on 2017/3/1.
 */
@Transactional
@Service("itemService")
public class ItemServiceImpl extends BasicWebService implements ItemService {

    @Autowired
    private ItemDataAccessManager itemDataAccessManager;

    @Override
    public JSONObject loaderItemTemplates() {
        List<ItemTemplate> itemTemplates = itemDataAccessManager.loaderItemTemplates(ItemStatusEnum.NORMAL);
        JSONArray itemTemplateArray = itemTemplates.stream().map(itemTemplate -> JSONObject.parseObject(JSONObject.toJSONString(itemTemplate))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemTemplateArray", itemTemplateArray);
        return success(response);
    }

    @Override
    public JSONObject getItemTemplateList() {
        int pageSize = getIntParameter("pageSize", GlobalConstantConfig.DEFAULT_PAGE_SIZE);
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);
        ItemTemplate template = new ItemTemplate();
        getEntityParameter(template);


        List<ItemTemplate> itemUnit = itemDataAccessManager.getItemTemplateList(template, pageSize, pageStartIndex);
        int count = itemDataAccessManager.getItemTemplateCount(template);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(itemUnit)));
        response.put("count", count);
        response.put("pageIndex", getIntParameter("pageIndex", 1) - 1);
        return success(response);
    }

    @Override
    public JSONObject getItemTemplate() {
        long itemTemplateId = getLongParameter("itemTemplateId");
        ItemTemplate itemTemplate = itemDataAccessManager.getItemTemplate(itemTemplateId, ItemStatusEnum.NORMAL);

        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemTemplate));
        return success(response);
    }

    @Override
    public JSONObject getItemTemplateByName() {
        String itemName = getUTF("itemName");
        ItemTemplate itemTemplate = itemDataAccessManager.getItemTemplateByName(itemName, ItemStatusEnum.NORMAL);

        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemTemplate));
        return success(response);
    }

    @Override
    public JSONObject getItemTemplateByBarcode() {
        String barcode = getUTF("barcode");

        ItemTemplate itemTemplate = itemDataAccessManager.getItemTemplateByBarcode(barcode);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemTemplate));
        return success(response);
    }

    @Override
    public JSONObject relationItemTemplates() {
        long itemTypeId = getLongParameter("itemTypeId");

        List<ItemTemplate> itemTemplates = itemDataAccessManager.getItemTemplates(itemTypeId, ItemStatusEnum.NORMAL);
        JSONArray itemTemplateArray = itemTemplates.stream().map(itemTemplate -> JSONObject.parseObject(JSONObject.toJSONString(itemTemplate))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemTemplateArray", itemTemplateArray);
        return success(response);
    }

    @Override
    public JSONObject loaderItemBrands() {
        List<ItemBrand> itemBrands = itemDataAccessManager.loaderItemBrands(ItemStatusEnum.NORMAL);

        JSONArray itemBrandArray = itemBrands.stream().map(itemBrand -> JSONObject.parseObject(JSONObject.toJSONString(itemBrand))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemBrandArray", itemBrandArray);
        return success(response);
    }

    @Override
    public JSONObject getItemBrand() {
        long itemBrandId = getLongParameter("itemBrandId");

        ItemBrand itemBrand = itemDataAccessManager.getItemBrand(itemBrandId, ItemStatusEnum.NORMAL);

        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemBrand));
        return success(response);
    }

    @Override
    public JSONObject loaderItemTypes() {
        List<ItemType> itemTypes = itemDataAccessManager.loaderItemTypes(ItemStatusEnum.NORMAL);

        JSONArray itemTypeArray = itemTypes.stream().map(itemType -> JSONObject.parseObject(JSONObject.toJSONString(itemType))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemTypeArray", itemTypeArray);
        return success(response);
    }

    @Override
    public JSONObject getItemType() {
        long itemTypeId = getLongParameter("itemTypeId");
        ItemType itemType = itemDataAccessManager.getItemType(itemTypeId, ItemStatusEnum.NORMAL);

        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemType));
        return success(response);
    }

    @Override
    public JSONObject relationItemTypes() {
        long parentItemTypeId = getLongParameter("parentItemTypeId");

        List<ItemType> itemTypes = itemDataAccessManager.relationItemTypes(parentItemTypeId, ItemStatusEnum.NORMAL);

        JSONArray itemTypeArray = itemTypes.stream().map(itemType -> JSONObject.parseObject(JSONObject.toJSONString(itemType))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemTypeArray", itemTypeArray);
        return success(response);
    }

    @Override
    public JSONObject loaderItemUnits() {
        List<ItemUnit> itemUnits = itemDataAccessManager.loaderItemUnits(ItemStatusEnum.NORMAL);

        JSONArray itemUnitArray = itemUnits.stream().map(itemUnit -> JSONObject.parseObject(JSONObject.toJSONString(itemUnit))).collect(Collectors.toCollection(JSONArray::new));

        JSONObject response = new JSONObject();
        response.put("itemUnitArray", itemUnitArray);

        return success(response);
    }

    @Override
    public JSONObject getItemUnit() {
        long itemUnitId = getLongParameter("itemUnitId");
        ItemUnit itemUnit = itemDataAccessManager.getItemUnit(itemUnitId, ItemStatusEnum.NORMAL);

        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(itemUnit));
        return success(response);
    }

    @Override
    public JSONObject getItemUnitList() {
        String title = getUTF("title", null);
        int status = getIntParameter("status", -1);
        int pageSize = getIntParameter("pageSize", GlobalConstantConfig.DEFAULT_PAGE_SIZE);
        int pageStartIndex = getPageStartIndex("pageIndex", pageSize);

        List<ItemUnit> itemUnit = itemDataAccessManager.getItemUnitList(title, status, pageSize, pageStartIndex);
        int count = itemDataAccessManager.getItemUnitListCount(title, status);

        JSONObject response = new JSONObject();
        response.put("data", JSONObject.parseArray(JSONObject.toJSONString(itemUnit)));
        response.put("count", count);
        response.put("pageIndex", getIntParameter("pageIndex", 1) - 1);
        return success(response);
    }

    @Override
    public JSONObject saveItemTemplate() {
        Long id = getLongParameter("id", 0);
        ItemTemplate template = new ItemTemplate();

        if (id != 0) template = itemDataAccessManager.getItemTemplate(id, ItemStatusEnum.ALL);
        getEntityParameter(template);
        template.setUploadTime(new Date());
        if (id != 0) {
            itemDataAccessManager.updateItemTemplate(template);
        } else {
            itemDataAccessManager.addItemTemplate(template);
        }

        template = itemDataAccessManager.getItemTemplate(template.getId(), ItemStatusEnum.ALL);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(template));
        return success(response);
    }

    @Override
    public JSONObject saveItemUnit() {
        Long id = getLongParameter("id", 0);

        ItemUnit unit = itemDataAccessManager.getItemUnit(id, ItemStatusEnum.ALL);
        if (unit == null) unit = new ItemUnit();

        unit.setTitle(getUTF("title"));

        if (id != 0) {
            itemDataAccessManager.updateItemUnit(unit);
        } else {
            itemDataAccessManager.addItemUnit(unit);
        }

        unit = itemDataAccessManager.getItemUnit(unit.getId(), ItemStatusEnum.ALL);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(unit));
        return success(response);
    }


    @Override
    public JSONObject saveItemType() {
        Long id = getLongParameter("id", 0);
        ItemType type = new ItemType();

        if (id != 0) type = itemDataAccessManager.getItemType(id, ItemStatusEnum.ALL);
        getEntityParameter(type);
        if (id != 0) {
            itemDataAccessManager.updateItemType(type);
        } else {
            itemDataAccessManager.addItemType(type);
        }

        type = itemDataAccessManager.getItemType(type.getId(), ItemStatusEnum.ALL);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(type));
        return success(response);
    }

    @Override
    public JSONObject saveItemBrand() {
        Long id = getLongParameter("id", 0);
        ItemBrand brand = new ItemBrand();

        if (id != 0) brand = itemDataAccessManager.getItemBrand(id, ItemStatusEnum.ALL);
        getEntityParameter(brand);
        brand.setCreateTime(new Date());
        if (id != 0) {
            itemDataAccessManager.updateItemBrand(brand);
        } else {
            itemDataAccessManager.addItemBrand(brand);
        }

        brand = itemDataAccessManager.getItemBrand(brand.getId(), ItemStatusEnum.ALL);
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(brand));
        return success(response);
    }

    @Override
    public JSONObject saveItemRelationship() {
        Long id = getLongParameter("id", 0);
        ItemRelationship rship = new ItemRelationship();

        if (id != 0) rship = itemDataAccessManager.getItemRelationship(id);
        getEntityParameter(rship);
        rship.setCreateTime(new Date());
        if (id != 0) {
            itemDataAccessManager.updateItemRelationship(rship);
        } else {
            itemDataAccessManager.addItemRelationship(rship);
        }

        rship = itemDataAccessManager.getItemRelationship(rship.getId());
        JSONObject response = JSONObject.parseObject(JSONObject.toJSONString(rship));
        return success(response);
    }

}