package com.xlibao.passport.data.mapper.location;

import com.xlibao.metadata.passport.PassportCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassportCityMapper {

    List<PassportCity> loaderCitys(@Param("provinceId") long provinceId);
}