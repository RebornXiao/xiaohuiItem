package com.xlibao.purchase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * @author chinahuangxc on 2017/1/24.
 */
@Configuration
@Lazy(false)
public class ConfigFactory {

    @Autowired
    private DomainNameConfig domainNameConfig;

    private static DomainNameConfig domainName;

    @PostConstruct
    public void initialization() {
        domainName = domainNameConfig;

        //DataCacheApplicationContextLoaderNotify.setLocationRemoteServiceURL(domainName.passportRemoteURL);
        //LocationDataCacheService.initLocationCache();

        //DataCacheApplicationContextLoaderNotify.setItemRemoteServiceURL(domainName.itemRemoteURL);
        //ItemDataCacheService.initItemCache();
    }

    public static DomainNameConfig getDomainNameConfig() {
        return domainName;
    }

}