package com.xlibao.order.config;

import com.xlibao.common.support.PassportRemoteService;
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
    @Autowired
    private OrderConfig orderConfig;

    private static DomainNameConfig domainName;
    private static OrderConfig order;

    @PostConstruct
    public void initialization() {
        domainName = domainNameConfig;

        PassportRemoteService.setPassportRemoteServiceURL(domainName.passportRemoteURL);

        order = orderConfig;
    }

    public static DomainNameConfig getDomainNameConfig() {
        return domainName;
    }

    public static OrderConfig getOrderConfig() {
        return order;
    }
}