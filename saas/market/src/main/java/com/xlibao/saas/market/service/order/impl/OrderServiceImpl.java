package com.xlibao.saas.market.service.order.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.service.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("orderService")
public class OrderServiceImpl extends BasicWebService implements OrderService {
}