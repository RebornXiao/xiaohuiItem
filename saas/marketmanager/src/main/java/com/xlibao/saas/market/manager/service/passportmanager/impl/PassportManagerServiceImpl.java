package com.xlibao.saas.market.manager.service.passportmanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.common.http.HttpUtils;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.passportmanager.PassportManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("passportManagerService")
public class PassportManagerServiceImpl extends BasicWebService implements PassportManagerService {

    @Override
    public boolean passportLogin(String userName, String passWord) {
        //String result = HttpUtils.get()
        return false;
    }

    public JSONObject getStreets(long districtId) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/loaderStreets.do?districtId=" + districtId);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

    public JSONObject getStreetsToMap(long districtId) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/loaderStreets.do?districtId=" + districtId);
        JSONObject response = JSONObject.parseObject(json);
        JSONObject json_objs = new JSONObject();
        json_objs.put("code", response.getIntValue("code"));
        json_objs.put("msg", response.getString("msg"));

        Map map = new HashMap<>();
        map.put(10000L, "可爱");
        map.put(20000L, "天在");

        json_objs.put("datas", map);

        return json_objs;
    }

    public JSONObject getStreetJson(long streetId) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/getStreet.do?streetId=" + streetId);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }


    public PassportProvince searchProvinceByName(String name) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/searchProvinceByName.do?name=" + name);
        JSONObject response = JSONObject.parseObject(json);
        if(response.getInteger("code") == 0) {
            return JSONObject.parseObject(response.getJSONObject("response").getString("data"), PassportProvince.class);
        }
        return null;
    }

    public PassportCity searchCityByName(String name) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/searchCityByName.do?name=" + name);
        JSONObject response = JSONObject.parseObject(json);
        if(response.getInteger("code") == 0) {
            return JSONObject.parseObject(response.getJSONObject("response").getString("data"), PassportCity.class);
        }
        return null;
    }

    public PassportArea searchAreaByName(String name) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/searchAreaByName.do?name=" + name);
        JSONObject response = JSONObject.parseObject(json);
        if(response.getInteger("code") == 0) {
            return JSONObject.parseObject(response.getJSONObject("response").getString("data"), PassportArea.class);
        }
        return null;
    }

    public PassportStreet searchStreetByName(long districtId, String name) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/searchStreetByName.do?name=" + name + "&districtId=" + districtId);
        JSONObject response = JSONObject.parseObject(json);
        if(response.getInteger("code") == 0) {
            return JSONObject.parseObject(response.getJSONObject("response").getString("data"), PassportStreet.class);
        }
        return null;
    }

    public PassportStreet getStreet(long streetId) {
        JSONObject json = getStreetJson(streetId);
        if(json.getInteger("code") == 0) {
            return JSONObject.parseObject(json.getJSONObject("response").getString("data"), PassportStreet.class);
        }
        return null;
    }

    public JSONObject streetEditSave(long id, long areaId, String name) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().passportRemoteURL + "/passport/location/streetEditSave.do?id=" + id + "&areaId=" + areaId + "&name=" + name);
        return JSONObject.parseObject(json);
    }
}