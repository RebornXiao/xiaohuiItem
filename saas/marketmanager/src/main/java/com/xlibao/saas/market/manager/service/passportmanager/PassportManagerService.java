package com.xlibao.saas.market.manager.service.passportmanager;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface PassportManagerService {

    public PassportProvince searchProvinceByName(String name);

    public PassportCity searchCityByName(String name);

    public PassportArea searchAreaByName(String name);

    public PassportStreet getStreet(long streetId);

    public boolean passportLogin(String userName, String passWord);

    public JSONObject getStreets(long districtId);

    public JSONObject getStreetsToMap(long districtId);

    public JSONObject getStreetJson(long streetId);

}
