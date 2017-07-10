package com.xlibao.saas.market.service.market.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.service.market.MarketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("marketService")
public class MarketServiceImpl extends BasicWebService implements MarketService {
}
