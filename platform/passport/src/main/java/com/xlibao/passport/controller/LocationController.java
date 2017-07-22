package com.xlibao.passport.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.passport.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Controller
@RequestMapping(value = "/passport/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @ResponseBody
    @RequestMapping("loaderProvinces")
    public JSONObject loaderProvinces() {
        return locationService.loaderProvinces();
    }

    @ResponseBody
    @RequestMapping("loaderCitys")
    public JSONObject loaderCitys() {
        return locationService.loaderCitys();
    }

    @ResponseBody
    @RequestMapping("loaderDistricts")
    public JSONObject loaderDistricts() {
        return locationService.loaderDistricts();
    }

    @ResponseBody
    @RequestMapping("loaderStreets")
    public JSONObject loaderStreets() {
        return locationService.loaderStreets();
    }
}