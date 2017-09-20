package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseSupplier;

public interface PurchaseSupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseSupplier record);

    int insertSelective(PurchaseSupplier record);

    PurchaseSupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseSupplier record);

    int updateByPrimaryKey(PurchaseSupplier record);
}