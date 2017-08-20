package com.xlibao.saas.market.data.mapper.market;

import com.xlibao.market.data.model.MarketShelvesManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketShelvesManagerMapper {

    int createShelves(MarketShelvesManager marketShelvesManager);

    List<MarketShelvesManager> getShelvesDatas(@Param("marketId") long marketId);
}