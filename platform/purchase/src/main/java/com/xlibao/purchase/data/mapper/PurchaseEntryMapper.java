package com.xlibao.advert.data.dao;

import com.xlibao.metadata.purchase.PurchaseEntry;

public interface PurchaseEntryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseEntry record);

    int insertSelective(PurchaseEntry record);

    PurchaseEntry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseEntry record);

    int updateByPrimaryKey(PurchaseEntry record);
}