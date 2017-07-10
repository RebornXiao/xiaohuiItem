package com.xlibao.passport.data.mapper.passport;

import com.xlibao.metadata.passport.PassportProperties;
import org.apache.ibatis.annotations.Param;

public interface PassportPropertiesMapper {

    int createPassportProperties(PassportProperties properties);

    PassportProperties getPassportProperties(@Param("passportId") long passportId, @Param("type") int type, @Param("k") String k);
}