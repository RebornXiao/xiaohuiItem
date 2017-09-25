package com.xlibao.purchase.data.mapper;

import com.xlibao.purchase.data.model.PurchaseCommodityStores;

public interface PurchaseCommodityStoresMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseCommodityStores record);

    int insertSelective(PurchaseCommodityStores record);

    PurchaseCommodityStores selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseCommodityStores record);

    int updateByPrimaryKey(PurchaseCommodityStores record);
}