package com.xlibao.saas.market.core.service.support;

import com.xlibao.saas.market.core.service.application.ApplicationService;
import com.xlibao.saas.market.core.service.application.impl.ApplicationServiceImpl;
import com.xlibao.saas.market.core.service.hardware.HardwareService;
import com.xlibao.saas.market.core.service.hardware.impl.HardwareServiceImpl;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class SupportFactory {

    private static final SupportFactory instance = new SupportFactory();

    private ApplicationService applicationService = new ApplicationServiceImpl();

    private HardwareService hardwareService = new HardwareServiceImpl();

    private SupportFactory() {}

    public static SupportFactory getInstance() {
        return instance;
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public HardwareService getHardwareService() {
        return hardwareService;
    }
}
