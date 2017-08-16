package com.xlibao.saas.market.manager.controller;

import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.LogicConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/marketmanager/passport")
public class PassportManagerController extends BaseController {

    @RequestMapping("/login")
    public String login(ModelMap map) {

        String userName = getUTF("name");
        String passWord = getUTF("password");

        //登陆

        map.put("name", "this is free maker test!!!");

        return "user/login";
    }

    @RequestMapping("/index")
    public String index(ModelMap map) {
        //首页相关统计内容

        return jumpPage(map, LogicConfig.FTL_INDEX, LogicConfig.TAB_INDEX, LogicConfig.TAB_INDEX);
    }
}