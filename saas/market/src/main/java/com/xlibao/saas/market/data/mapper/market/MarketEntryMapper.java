package com.xlibao.saas.market.data.mapper.market;

import com.xlibao.market.data.model.MarketEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketEntryMapper {

    List<MarketEntry> loaderMarkets();

    MarketEntry getMarket(@Param("marketId") long marketId);

    List<MarketEntry> getMarkets(@Param("streetId") long streetId);

    MarketEntry getMarketForPassport(@Param("passportId") long passportId);

    List<MarketEntry> searchMarkets(@Param("searchModel")MarketEntry searchModel, @Param("pageSize") int pageSize, @Param("pageStartIndex") int pageStartIndex);

    int searchMarketsCount(@Param("searchModel")MarketEntry searchModel);

    List<MarketEntry> getAllMarkets();

}