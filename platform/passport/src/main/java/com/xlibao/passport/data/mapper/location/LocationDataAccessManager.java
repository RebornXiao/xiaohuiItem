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

    public List<PassportCity> loaderCitys() {
        return cityMapper.loaderCitys();
    }

    public List<PassportArea> loaderDistricts() {
        return areaMapper.loaderDistricts();
    }

    public List<PassportStreet> loaderStreets() {
        return streetMapper.loaderStreets();
    }
}