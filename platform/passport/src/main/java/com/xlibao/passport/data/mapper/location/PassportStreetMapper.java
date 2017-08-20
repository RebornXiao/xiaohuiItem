package com.xlibao.passport.data.mapper.location;

import com.xlibao.metadata.passport.PassportStreet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassportStreetMapper {

    List<PassportStreet> loaderStreets(@Param("districtId") long districtId);

    PassportStreet getStreet(@Param("streetId") long streetId);

}