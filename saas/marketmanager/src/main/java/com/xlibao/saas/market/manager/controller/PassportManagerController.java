package com.xlibao.saas.market.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/market/manager/passport")
public class PassportManagerController {

    public void index() {

    }


    @RequestMapping("/login")
    public String login(ModelMap map) {
        map.put("name", "this is freemaker test!!!");
        return "login";
    }
}