package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseCommodityStores;

public interface PurchaseCommodityStoresMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseCommodityStores record);

    int insertSelective(PurchaseCommodityStores record);

    PurchaseCommodityStores selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseCommodityStores record);

    int updateByPrimaryKey(PurchaseCommodityStores record);
}