package com.xlibao.saas.market.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import com.xlibao.saas.market.manager.BaseController;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.config.LogicConfig;
import com.xlibao.saas.market.manager.service.marketmanager.MarketManagerService;
import com.xlibao.saas.market.manager.service.passportmanager.PassportManagerService;
import com.xlibao.saas.market.manager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Controller
@RequestMapping(value = "/marketmanager/market")
public class MarketManagerController extends BaseController {

    @Autowired
    private MarketManagerService marketManagerService;

    @Autowired
    private PassportManagerService passportManagerService;

    @RequestMapping("/markets")
    public String markets(ModelMap map) {

        String province = getUTF("province", "");
        String city = getUTF("city", "");
        String district = getUTF("district", "");

        long provinceId = getLongParameter("provinceId", 0);
        long cityId = getLongParameter("city", 0);
        long districtId = getLongParameter("district", 0);

        String street = getUTF("street", "");
        long streetId = getLongParameter("streetId", -1);

        int type = getIntParameter("type", -1);
        int status = getIntParameter("status", -1);
        int deliveryMode = getIntParameter("deliveryMode", -1);

        MarketEntry entry = new MarketEntry();
        entry.setProvince(province);
        entry.setCity(city);
        entry.setDistrict(district);
        entry.setStreet(street);
        entry.setStreetId(streetId);
        entry.setType(type);
        entry.setStatus(status);
        entry.setDeliveryMode(deliveryMode);

        int pageSize = getPageSize();
        int pageIndex = getIntParameter("pageIndex", 1);

        JSONObject marketJson = marketManagerService.searchMarkets(entry, pageSize, pageIndex);

        if (marketJson.getIntValue("code") != 0) {
            return remoteFail(map, marketJson, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
        }

        JSONObject response = marketJson.getJSONObject("response");

        map.put("markets", response.getJSONArray("data"));
        map.put("count", response.getIntValue("count"));

        //////////////////
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("streetId", streetId);

        JSONObject streetJson = null;

        if (districtId != 0) {
            streetJson = passportManagerService.getStreets(districtId);
            if (streetJson.getInteger("code") == 0) {
                map.put("streets", streetJson.getJSONObject("response").getJSONArray("datas"));
            }
        }

        map.put("type", type);
        map.put("status", status);
        map.put("deliveryMode", deliveryMode);

        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);

        return jumpPage(map, LogicConfig.FTL_MARKET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }

    @RequestMapping("/merketEdit")
    public String merketEdit(ModelMap map) {
        long id = getLongParameter("id", 0);
        if (id != 0) {
            JSONObject itemJson = marketManagerService.getMarket(id);
            if (itemJson.getIntValue("code") != 0) {
                return remoteFail(map, itemJson, LogicConfig.TAB_ITEM, LogicConfig.TAB_ITEM_TEMPLATE);
            }
            MarketEntry entry = JSONObject.parseObject(itemJson.getJSONObject("response").getString("data"), MarketEntry.class);
            map.put("market", entry);
            //拿省
            String provinceName = entry.getProvince();
            if (CommonUtils.isNotNullString(provinceName)) {
                PassportProvince province = passportManagerService.searchProvinceByName(provinceName);
                if (province != null) {
                    map.put("provinceId", province.getId());
                }
            }
            //拿市
            String cityName = entry.getCity();
            if (CommonUtils.isNotNullString(cityName)) {
                PassportCity city = passportManagerService.searchCityByName(cityName);
                if (city != null) {
                    map.put("cityId", city.getId());
                }
            }
            //拿区
            String areaName = entry.getDistrict();
            if (CommonUtils.isNotNullString(areaName)) {
                PassportArea area = passportManagerService.searchAreaByName(areaName);
                if (area != null) {
                    map.put("areaId", area.getId());
                }
            }
            //拿街道
            if (entry.getStreetId() != 0) {
                //拿到这个街道
                JSONObject streetJson = passportManagerService.getStreetJson(entry.getStreetId());
                if (streetJson.getIntValue("code") == 0) {
                    PassportStreet street = JSONObject.parseObject(streetJson.getJSONObject("response").getString("data"), PassportStreet.class);
                    //拿所有数据
                    JSONObject streetsJson = passportManagerService.getStreets(street.getAreaId());
                    if (streetsJson.getInteger("code") == 0) {
                        map.put("streets", streetsJson.getJSONObject("response").getJSONArray("datas"));
                        map.put("streetId", entry.getStreetId());
                    }
                }
            }
        }
        return jumpPage(map, LogicConfig.FTL_MARKET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_LIST);
    }

    @ResponseBody
    @RequestMapping("/getStreets")
    public JSONObject getStreets() {
        JSONObject json = passportManagerService.getStreets(getLongParameter("districtId"));
        if (json.getIntValue("code") == 0) {
            Map map = new HashMap<>();
            JSONArray array = json.getJSONObject("response").getJSONArray("datas");
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                map.put(obj.getInteger("id"), obj.getString("name"));
            }
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("datas", map);
            return result;
        } else {
            JSONObject result = new JSONObject();
            result.put("code", 1);
            result.put("msg", json.getString("msg"));
            return result;
        }
    }

    @RequestMapping("/streets")
    public String streets(ModelMap map) {

        //如果没有选区域，则默认为0
        long provinceId = getLongParameter("provinceId", 10018);//默认广东省
        long cityId = getLongParameter("cityId", 10212);//默认广州市
        long areaId = getLongParameter("areaId", 11919);//默认天河区

        if (areaId != 0) {
            JSONObject streets = passportManagerService.getStreets(areaId);
            if (streets.getIntValue("code") == 0) {
                map.put("streets", streets.getJSONObject("response").getJSONArray("datas"));
            }
        }

        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", areaId);

        return jumpPage(map, LogicConfig.FTL_MARKET_STREET_LIST, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
    }

    @RequestMapping("/streetEdit")
    public String streetEdit(ModelMap map) {
        //如果没有选区域，则默认为0
        long provinceId = getLongParameter("provinceId", 10018);//默认广东省
        long cityId = getLongParameter("cityId", 10212);//默认广州市
        long areaId = getLongParameter("areaId", 11919);//默认天河区
        long id = getLongParameter("id", 0);

        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("areaId", areaId);

        if (id != 0) {
            PassportStreet street = passportManagerService.getStreet(id);
            if (street == null) {
                return jumpPage(map, LogicConfig.FTL_MARKET_STREET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
            }
            map.put("street", street);
        }

        return jumpPage(map, LogicConfig.FTL_MARKET_STREET_EDIT, LogicConfig.TAB_MARKET, LogicConfig.TAB_MARKET_STREET_LIST);
    }
}