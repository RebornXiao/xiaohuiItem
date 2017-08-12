package com.xlibao.saas.market.core.service.support;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class SupportFactory {

    private static final SupportFactory instance = new SupportFactory();

    private SupportFactory() {}

    public static SupportFactory getInstance() {
        return instance;
    }
}
