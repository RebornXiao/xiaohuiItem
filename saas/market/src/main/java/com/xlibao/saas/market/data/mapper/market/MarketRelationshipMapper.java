package com.xlibao.saas.market.data.mapper.market;

import com.xlibao.market.data.model.MarketRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketRelationshipMapper {

    List<MarketRelationship> myFocusMarkets(@Param("passportId") long passportId, @Param("type") int type);

    MarketRelationship getRelationship(@Param("passportId") long passportId, @Param("marketId") long marketId, @Param("type") int type);

    List<MarketRelationship> getRelationships(@Param("marketId") long marketId, @Param("type") int type);
}