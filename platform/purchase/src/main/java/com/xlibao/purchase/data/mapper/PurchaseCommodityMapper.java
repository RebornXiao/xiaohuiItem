package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseCommodity;

public interface PurchaseCommodityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseCommodity record);

    int insertSelective(PurchaseCommodity record);

    PurchaseCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseCommodity record);

    int updateByPrimaryKey(PurchaseCommodity record);
}