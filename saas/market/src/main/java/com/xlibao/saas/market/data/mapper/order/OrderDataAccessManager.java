package com.xlibao.saas.market.data.mapper.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Transactional
@Component
public class OrderDataAccessManager {

    @Autowired
    private MarketOrderPaymentLoggerMapper orderPaymentLoggerMapper;
}