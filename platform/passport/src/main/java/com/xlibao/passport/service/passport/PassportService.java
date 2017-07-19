package com.xlibao.passport.service.passport;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.metadata.passport.Passport;

/**
 * @author chinahuangxc on 2017/2/6.
 */
public interface PassportService {

    JSONObject registerPassport();

    JSONObject loginPassport();

    JSONObject modifyPassword();

    JSONObject forgetPassword();

    JSONObject resetMobileNumber();

    JSONObject authentication();

    JSONObject versionUpgrade();

    JSONObject changeAccessToken();

    Passport getPassport(long passportId);
}
