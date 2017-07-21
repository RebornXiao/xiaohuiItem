package com.xlibao.datacache.location;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.http.HttpUtils;
import com.xlibao.datacache.DataCacheApplicationContextLoaderNotify;
import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chinahuangxc on 2017/7/21.
 */
public class LocationRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(LocationRemoteService.class);

    static List<PassportProvince> loaderProvinces() {
        String url = DataCacheApplicationContextLoaderNotify.getItemRemoteServiceURL() + "passport/location/loaderProvinces";
        return getLocations(url, PassportProvince.class);
    }

    static List<PassportCity> loaderCitys() {
        String url = DataCacheApplicationContextLoaderNotify.getItemRemoteServiceURL() + "passport/location/loaderCitys";
        return getLocations(url, PassportCity.class);
    }

    static List<PassportArea> loaderDistricts() {
        String url = DataCacheApplicationContextLoaderNotify.getItemRemoteServiceURL() + "passport/location/loaderDistricts";
        return getLocations(url, PassportArea.class);
    }

    private static <T> List<T> getLocations(String url, Class<T> t) {
        String result = HttpUtils.get(url);
        JSONObject response = JSONObject.parseObject(result);

        int code = response.getIntValue("code");
        if (code != 0) {
            throw new XlibaoRuntimeException(code, response.getString("msg"));
        }
        response = response.getJSONObject("response");

        JSONArray provinceArray = response.getJSONArray("datas");

        List<T> provinces = new ArrayList<>();

        for (int i = 0; i < provinceArray.size(); i++) {
            provinces.add(JSONObject.parseObject(provinceArray.getString(i), t));
        }
        return provinces;
    }
}