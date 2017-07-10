package com.xlibao.saas.market.manager.service.marketmanager.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.service.marketmanager.MarketManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("marketManagerService")
public class MarketManagerServiceImpl extends BasicWebService implements MarketManagerService {
}