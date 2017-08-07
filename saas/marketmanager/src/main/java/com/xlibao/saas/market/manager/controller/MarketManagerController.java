package com.xlibao.saas.market.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/market")
public class MarketManagerController {

    /**
     * <pre>
     *      <b>获取货架数据</b>
     * </pre>
     *
     * @return String 页面地址
     */
    @RequestMapping(value = "marketShelvesData")
    public String marketShelvesData() {
        return "";
    }
}
