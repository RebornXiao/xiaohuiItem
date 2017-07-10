package com.xlibao.saas.market.manager.service.itemmanager.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.service.itemmanager.ItemManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("itemManagerService")
public class ItemManagerServiceImpl extends BasicWebService implements ItemManagerService {
}