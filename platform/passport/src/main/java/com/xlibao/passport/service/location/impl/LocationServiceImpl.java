package com.xlibao.passport.service.location.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import com.xlibao.passport.config.ConfigFactory;
import com.xlibao.passport.data.mapper.location.LocationDataAccessManager;
import com.xlibao.passport.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static sun.tools.jstat.Alignment.keySet;

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


        //打印成js使用
//        StringBuilder sb1 = new StringBuilder();
//        sb1.append("{");
//        for (int i = 0; i < provinces.size(); i++) {
//            //{1:'北京市',
//            sb1.append(provinces.get(i).getId()).append(":'").append(provinces.get(i).getName()).append("',");
//        }
//        sb1.append("}");
//        System.err.println(sb1.toString());


        JSONArray response = provinces.stream().map(province -> JSONObject.parseObject(JSONObject.toJSONString(province))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject loaderCitys() {
        long provinceId = getLongParameter("provinceId", 0);
        List<PassportCity> citys = locationDataAccessManager.loaderCitys(provinceId);

        //打印成js使用
//        Map<Long, List<PassportCity>> map = new HashMap<>();
//        for (int i = 0; i < citys.size(); i++) {
//            PassportCity city = citys.get(i);
//            long pid = city.getProvinceId();
//            List<PassportCity> pcs = map.get(pid);
//            if (pcs == null) {
//                pcs = new ArrayList<>();
//                map.put(pid, pcs);
//            }
//            pcs.add(city);
//        }
//
//        Iterator<Long> it = map.keySet().iterator();
//        while (it.hasNext()) {
//            Long id = it.next();
//            List<PassportCity> pc = map.get(id);
//            StringBuilder sb1 = new StringBuilder();
//            sb1.append("'").append(id).append("':{");
//            for (int i = 0; i < pc.size(); i++) {
//                sb1.append(pc.get(i).getId()).append(":'").append(pc.get(i).getName()).append("',");
//            }
//            sb1.append("},");
//            System.err.println(sb1.toString());
//        }

        JSONArray response = citys.stream().map(city -> JSONObject.parseObject(JSONObject.toJSONString(city))).collect(Collectors.toCollection(JSONArray::new));
        return success(response);
    }

    @Override
    public JSONObject loaderDistricts() {
        long cityId = getLongParameter("cityId", 0);
        List<PassportArea> districts = locationDataAccessManager.loaderDistricts(cityId);

        //打印成js使用
//        Map<Long, List<PassportArea>> map = new HashMap<>();
//        for (int i = 0; i < districts.size(); i++) {
//            PassportArea city = districts.get(i);
//            long pid = city.getCityId();
//            List<PassportArea> pcs = map.get(pid);
//            if (pcs == null) {
//                pcs = new ArrayList<>();
//                map.put(pid, pcs);
//            }
//            pcs.add(city);
//        }
//
//        Iterator<Long> it = map.keySet().iterator();
//        while (it.hasNext()) {
//            Long id = it.next();
//            List<PassportArea> pc = map.get(id);
//            StringBuilder sb1 = new StringBuilder();
//            sb1.append("'").append(id).append("':{");
//            for (int i = 0; i < pc.size(); i++) {
//                sb1.append(pc.get(i).getId()).append(":'").append(pc.get(i).getName()).append("',");
//            }
//            sb1.append("},");
//            System.err.println(sb1.toString());
//        }

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