package com.xlibao.saas.market.data.mapper.item;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketSearchHistoryMapper {

    List<String> loaderHotSearch(@Param("marketId") long marketId, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);
}