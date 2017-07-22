package com.xlibao.passport.data.mapper.location;

import com.xlibao.metadata.passport.PassportArea;
import com.xlibao.metadata.passport.PassportCity;
import com.xlibao.metadata.passport.PassportProvince;
import com.xlibao.metadata.passport.PassportStreet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/20.
 */
@Component
public class LocationDataAccessManager {

    @Autowired
    private PassportProvinceMapper provinceMapper;
    @Autowired
    private PassportCityMapper cityMapper;
    @Autowired
    private PassportAreaMapper areaMapper;
    @Autowired
    private PassportStreetMapper streetMapper;

    public List<PassportProvince> loaderProvinces() {
        return provinceMapper.loaderProvinces();
    }

    public List<PassportCity> loaderCitys(long provinceId) {
        return cityMapper.loaderCitys(provinceId);
    }

    public List<PassportArea> loaderDistricts(long cityId) {
        return areaMapper.loaderDistricts(cityId);
    }

    public List<PassportStreet> loaderStreets(long districtId) {
        return streetMapper.loaderStreets(districtId);
    }
}