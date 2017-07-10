package com.xlibao.item.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/4/30.
 */
@Controller
@RequestMapping(value = "/item")
public class ItemOpenApiController {

    @ResponseBody
    @RequestMapping(value = "/findSingleProduct")
    public JSONObject findSingleProduct() {
        return null;
    }
}