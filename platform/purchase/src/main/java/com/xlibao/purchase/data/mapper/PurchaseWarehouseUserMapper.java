package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseWarehouseUser;

public interface PurchaseWarehouseUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseWarehouseUser record);

    int insertSelective(PurchaseWarehouseUser record);

    PurchaseWarehouseUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseWarehouseUser record);

    int updateByPrimaryKey(PurchaseWarehouseUser record);
}