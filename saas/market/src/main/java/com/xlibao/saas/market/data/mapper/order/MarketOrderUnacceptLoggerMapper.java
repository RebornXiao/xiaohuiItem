package com.xlibao.saas.market.data.mapper.order;

import com.xlibao.market.data.model.MarketOrderUnacceptLogger;
import org.apache.ibatis.annotations.Param;

public interface MarketOrderUnacceptLoggerMapper {

    int createUnAcceptLogger(MarketOrderUnacceptLogger orderUnacceptLogger);

    MarketOrderUnacceptLogger getOrderUnacceptLogger(@Param("passportId") long passportId, @Param("orderSequenceNumber") String orderSequenceNumber);

    int removeUnAcceptLoggers(@Param("orderSequenceNumber") String orderSequenceNumber);
}