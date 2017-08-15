package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.market.data.model.MarketItemLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketItemLocationMapper {

    List<MarketItemLocation> getItemLocations(@Param("itemId") long itemId);

    int updateItemLocationStock(@Param("locationId") long locationId, @Param("decrementStock") int decrementStock);
}