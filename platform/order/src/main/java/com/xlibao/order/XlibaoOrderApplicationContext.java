package com.xlibao.order;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * @author chinahuangxc on 2017/2/16.
 */
@Configuration
@Lazy(false)
public class XlibaoOrderApplicationContext {

    private Logger logger = Logger.getLogger(XlibaoOrderApplicationContext.class);

    @Autowired
    private ApplicationContextLoaderNotify applicationContextLoaderNotify;

    @PostConstruct
    public void applicatioinContextInited() {
        logger.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 订单(Order)系统开始初始化状态 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        applicationContextLoaderNotify.applicationContextLoaderNotify();
        logger.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 订单(Order)系统初始化状态完成 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
    }
}
