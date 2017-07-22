package com.xlibao.saas.market.data.mapper.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Component
public class OrderDataAccessManager {

    @Autowired
    private MarketOrderPaymentLoggerMapper orderPaymentLoggerMapper;
}