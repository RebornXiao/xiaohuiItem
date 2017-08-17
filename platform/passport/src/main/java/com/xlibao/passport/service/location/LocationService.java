package com.xlibao.passport.service.location;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chinahuangxc on 2017/7/21.
 */
public interface LocationService {

    JSONObject loaderLocations();

    JSONObject loaderProvinces();

    JSONObject loaderCitys();

    JSONObject loaderDistricts();

    JSONObject loaderStreets();
}
