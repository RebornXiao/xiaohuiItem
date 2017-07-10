package com.xlibao.passport.controller;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.metadata.passport.Passport;
import com.xlibao.passport.service.partner.TencentService;
import com.xlibao.passport.service.passport.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/3/20.
 */
@Controller
@RequestMapping(value = "/openApi")
public class OpenApiController extends BasicWebService {

    @Autowired
    private PassportService passportService;
    @Autowired
    private TencentService tencentService;

    @ResponseBody
    @RequestMapping(value = "getPassport")
    public JSONObject getPassport() {
        long passportId = getLongParameter("passportId");
        Passport passport = passportService.getPassport(passportId);
        passport.setPassword("");

        JSONObject parameters = JSONObject.parseObject(JSONObject.toJSONString(passport));
        return success(parameters);
    }

    /**
     * <pre>
     *     <b>微信授权</b>
     *
     *     <b>访问地址：</b>http://domainName/openApi/weixinAuthorization
     *     <b>访问方式：</b>GET/POST，推荐使用POST
     *
     *     <b>参数：</b>
     *          <b>code</b> - String 前端请求微信授权后获得的code，必填参数
     *
     *     <b>返回：</b>
     *          <b>openId</b> - String 微信授权后返回的用户在该公众号下的openId(同一个公众号下唯一)
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "weixinAuthorization")
    public JSONObject weixinAuthorization() {
        return tencentService.weixinAuthorization();
    }
}