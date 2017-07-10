package com.xlibao.saas.market.manager.service.passportmanager.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.service.passportmanager.PassportManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("passportManagerService")
public class PassportManagerServiceImpl extends BasicWebService implements PassportManagerService {
}