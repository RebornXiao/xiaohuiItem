package com.xlibao.saas.market.data.mapper.order;

import com.xlibao.saas.market.data.model.MarketOrderStatusLogger;
import org.apache.ibatis.annotations.Param;

public interface MarketOrderStatusLoggerMapper {

    int createOrderStatusLogger(MarketOrderStatusLogger orderStatusLogger);

    MarketOrderStatusLogger getOrderStatusLogger(@Param("orderSequenceNumber") String orderSequenceNumber, @Param("localStatus") int localStatus);
}