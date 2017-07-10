package com.xlibao.saas.market.service.item.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.service.item.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("itemService")
public class ItemServiceImpl extends BasicWebService implements ItemService {
}