package com.xlibao.passport.data.mapper.passport;

import com.xlibao.metadata.passport.Passport;
import org.apache.ibatis.annotations.Param;

public interface PassportMapper {

    int createPassport(Passport passport);

    Passport getPassport(@Param("id") long id);

    int modifyPassportPassword(@Param("id") long id, @Param("password") String password);

    int modifyAccessToken(@Param("id") long id, @Param("accessToken") String accessToken);
}