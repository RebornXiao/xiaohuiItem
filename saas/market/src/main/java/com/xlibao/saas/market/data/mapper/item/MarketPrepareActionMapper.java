package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.market.data.model.MarketPrepareAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketPrepareActionMapper {

    int createPrepareAction(MarketPrepareAction prepareAction);

    MarketPrepareAction getPrepareAction(@Param("marketId") long marketId, @Param("itemLocation") String itemLocation, @Param("status") int status);

    List<MarketPrepareAction> getPrepareActionsForLocationSet(@Param("marketId") long marketId, @Param("locationSet") String locationSet, @Param("status") int status);

    List<MarketPrepareAction> getUnCompletePrepareActions(@Param("marketId") long marketId, @Param("status") int status, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    int modifyPrepareActionStatus(@Param("marketId") long marketId, @Param("itemLocation") String itemLocation, @Param("matchStatus") int matchStatus, @Param("status") int status, @Param("time") String time);
}