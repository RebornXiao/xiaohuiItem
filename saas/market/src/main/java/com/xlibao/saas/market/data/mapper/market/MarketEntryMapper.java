package com.xlibao.saas.market.data.mapper.market;

import com.xlibao.saas.market.data.model.MarketEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketEntryMapper {

    List<MarketEntry> loaderMarkets();

    MarketEntry getMarket(@Param("marketId") long marketId);

    MarketEntry getMarketForPassport(@Param("passportId") long passportId);
}