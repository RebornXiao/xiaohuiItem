package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseWarehouse;

public interface PurchaseWarehouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseWarehouse record);

    int insertSelective(PurchaseWarehouse record);

    PurchaseWarehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseWarehouse record);

    int updateByPrimaryKey(PurchaseWarehouse record);
}