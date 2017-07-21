package com.xlibao.saas.market.manager.controller;

import com.xlibao.common.BasicWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/passport")
public class PassportManagerController extends BasicWebService {

    @RequestMapping("/login")
    public String login(ModelMap map) {
        map.put("name", "this is free maker test!!!");

        return "login";
    }
}