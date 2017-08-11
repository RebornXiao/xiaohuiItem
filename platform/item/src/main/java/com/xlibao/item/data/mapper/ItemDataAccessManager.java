package com.xlibao.item.data.mapper;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.constant.item.ItemStatusEnum;
import com.xlibao.metadata.item.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chinahuangxc on 2017/3/1.
 */
@Component
public class ItemDataAccessManager {

    @Autowired
    private ItemTemplateMapper templateMapper;
    @Autowired
    private ItemBrandMapper brandMapper;
    @Autowired
    private ItemTypeMapper typeMapper;
    @Autowired
    private ItemUnitMapper unitMapper;
    @Autowired
    private ItemRelationshipMapper relationshipMapper;


    public List<ItemTemplate> loaderItemTemplates(ItemStatusEnum statusEnum) {
        return templateMapper.loaderItemTemplates(statusEnum.getKey());
    }

    public List<ItemTemplate> getItemTemplates(long itemTypeId, ItemStatusEnum statusEnum) {
        return templateMapper.getItemTemplates(itemTypeId, statusEnum.getKey());
    }

    public ItemTemplate getItemTemplate(long itemTemplateId, ItemStatusEnum statusEnum) {
        return templateMapper.getItemTemplate(itemTemplateId, statusEnum.getKey());
    }

    public ItemTemplate getItemTemplateByName(String itemName, ItemStatusEnum statusEnum) {
        return templateMapper.getItemTemplateByName(itemName, statusEnum.getKey());
    }

    public ItemTemplate getItemTemplateByBarcode(String barcode) {
        return templateMapper.getItemTemplateByBarcode(barcode);
    }

    public int addItemTemplate(ItemTemplate template) {
        return templateMapper.add(template);
        
    }

    public void updateItemTemplate(ItemTemplate template) {
        templateMapper.updateItemTemplate(template);
    }

    public List<ItemTemplate> getItemTemplateList(ItemTemplate template, int pageSize, int pageStartIndex) {
        return templateMapper.getItemTemplateList(template, pageSize, pageStartIndex);
    }

    public int getItemTemplateCount(ItemTemplate template) {
        return templateMapper.getItemTemplateListCount(template);
    }

    public List<ItemBrand> loaderItemBrands(ItemStatusEnum statusEnum) {
        return brandMapper.loaderItemBrands(statusEnum.getKey());
    }

    public ItemBrand getItemBrand(long itemBrandId, ItemStatusEnum statusEnum) {
        return brandMapper.geItemBrand(itemBrandId, statusEnum.getKey());
    }

    public Long addItemBrand(ItemBrand brand) {
        return brandMapper.add(brand);
    }

    public int updateItemBrand(ItemBrand brand) {
        return brandMapper.update(brand);
    }

    public List<ItemType> loaderItemTypes(ItemStatusEnum statusEnum) {
        return typeMapper.loaderItemTypes(statusEnum.getKey());
    }

    public List<ItemType> relationItemTypes(long parentItemTypeId, ItemStatusEnum statusEnum) {
        return typeMapper.relationItemTypes(parentItemTypeId, statusEnum.getKey());
    }

    public ItemType getItemType(long itemTypeId, ItemStatusEnum statusEnum) {
        return typeMapper.getItemType(itemTypeId, statusEnum.getKey());
    }

    public Long addItemType(ItemType type) {
        return typeMapper.add(type);
    }

    public void updateItemType(ItemType type) {
        typeMapper.update(type);
    }

    public List<ItemUnit> loaderItemUnits(ItemStatusEnum statusEnum) {
        return unitMapper.loaderItemUnits(statusEnum.getKey());
    }

    public ItemUnit getItemUnit(long itemUnitId, ItemStatusEnum statusEnum) {
        return unitMapper.getItemUnit(itemUnitId, statusEnum.getKey());
    }


    public List<ItemUnit> getItemUnitList(String title, int status, int pageSize, int pageStartIndex) {
        return unitMapper.getItemUnitList(title, status, pageSize, pageStartIndex);
    }

    public int getItemUnitListCount(String title, int status) {
        return unitMapper.getItemUnitListCount(title, status);
    }

    public Long addItemUnit(ItemUnit unit) {
        return unitMapper.add(unit);
    }

    public void updateItemUnit(ItemUnit itemUnit) {
        unitMapper.update(itemUnit);
    }

    public ItemRelationship getItemRelationship(Long id) {
        return relationshipMapper.getItemRelationship(id);
    }

    public Long addItemRelationship(ItemRelationship itemRelationship) {
        return relationshipMapper.add(itemRelationship);
    }

    public void updateItemRelationship(ItemRelationship itemRelationship) {
        relationshipMapper.update(itemRelationship);
    }





    public List<ItemUnit> searchItemUnitByName(String searchKey, int pageSize, int pageStartIndex) {
        return unitMapper.searchItemUnitByName(searchKey, pageSize, pageStartIndex);
    }

    public Integer searchItemUnitCountByName(String searchKey) {
        return unitMapper.searchItemUnitCountByName(searchKey);
    }

    public int itemTypesCount(long parentItemTypeId) {
        return typeMapper.itemTypesCount(parentItemTypeId);
    }

    public List<ItemType> pageItemType(long parentItemTypeId, int pageSize, int pageStartIndex) {
        return typeMapper.pageItemType(parentItemTypeId, pageSize, pageStartIndex);
    }

    public List<ItemType> searchItemTypePageByName(String searchKey, int pageSize, int pageStartIndex) {
        return typeMapper.searchItemTypePageByName(searchKey, pageSize, pageStartIndex);
    }

    public int searchItemTypeCountByName(String searchKey) {
        return typeMapper.searchItemTypeCountByName(searchKey);
    }

    public List<ItemTemplate> searchItemTemplates(String searchType, String searchKey, int pageSize, int pageStartIndex) {
        return templateMapper.searchItemTemplates(searchType, searchKey, pageSize, pageStartIndex);
    }

    public int searchItemTemplateCount(String searchType, String searchKey) {
        return templateMapper.searchItemTemplateCount(searchType, searchKey);
    }
}