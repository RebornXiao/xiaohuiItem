package com.xlibao.saas.market.manager.service.ordermanager.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.service.ordermanager.OrderManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("orderManagerService")
public class OrderManagerServiceImpl extends BasicWebService implements OrderManagerService {
}