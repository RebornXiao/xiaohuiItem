package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.market.data.model.MarketItemLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketItemLocationMapper {

    int createItemLocation(MarketItemLocation itemLocation);

    MarketItemLocation getItemLocation(@Param("itemId") long itemId, @Param("location") String location);

    MarketItemLocation getItemLocationForMarket(@Param("marketId") long marketId, @Param("location") String location);

    List<MarketItemLocation> getItemLocations(@Param("itemId") long itemId);

    int offsetItemLocationStock(@Param("locationId") long locationId, @Param("decrementStock") int decrementStock);

    int removeItemLocation(@Param("locationId") long locationId);
}