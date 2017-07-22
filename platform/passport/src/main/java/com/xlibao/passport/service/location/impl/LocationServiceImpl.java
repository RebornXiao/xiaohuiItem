package com.xlibao.passport.service.location.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import com.xlibao.passport.data.mapper.location.LocationDataAccessManager;
import com.xlibao.passport.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chinahuangxc on 2017/7/21.
 */
@Service("locationService")
public class LocationServiceImpl extends BasicWebService implements LocationService {

    @Autowired
    private LocationDataAccessManager locationDataAccessManager;

    @Override
    public JSONObject loaderProvinces() {
        List<PassportProvince> provinces = locationDataAccessManager.loaderProvinces();

        JSONArray response = provinces.stream().map(province -> JSONObject.parseObject(JSONObject.toJSONString(province))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject loaderCitys() {
        long provinceId = getLongParameter("provinceId", 0);
        List<PassportCity> citys = locationDataAccessManager.loaderCitys(provinceId);

        JSONArray response = citys.stream().map(city -> JSONObject.parseObject(JSONObject.toJSONString(city))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject loaderDistricts() {
        long cityId = getLongParameter("cityId", 0);
        List<PassportArea> districts = locationDataAccessManager.loaderDistricts(cityId);

        JSONArray response = districts.stream().map(district -> JSONObject.parseObject(JSONObject.toJSONString(district))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject loaderStreets() {
        long districtId = getLongParameter("districtId", 0);
        List<PassportStreet> streets = locationDataAccessManager.loaderStreets(districtId);

        JSONArray response = streets.stream().map(street -> JSONObject.parseObject(JSONObject.toJSONString(street))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }
}