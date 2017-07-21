package com.xlibao.passport.data.mapper.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}