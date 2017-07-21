package com.xlibao.passport.service.partner.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.http.HttpUtils;
import com.xlibao.passport.config.ConfigFactory;
import com.xlibao.passport.service.partner.TencentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/6/9.
 */
@Transactional
@Service("tencentServiceImpl")
public class TencentServiceImpl extends BasicWebService implements TencentService {

    private final static Logger logger = LoggerFactory.getLogger(TencentServiceImpl.class);

    @Override
    public JSONObject weixinAuthorization() {
        String code = getUTF("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ConfigFactory.getPartner().getWeixinMpAppId() + "&secret=" + ConfigFactory.getPartner().getWeixinMpAppSecret() + "&code=" + code + "&grant_type=authorization_code";
        logger.info("微信授权登录请求链接：" + url);
        String result = HttpUtils.get(url);

        JSONObject response = JSONObject.parseObject(result);
        logger.info("微信授权登录，获取微信用户授权信息：" + response);
        int errorCode = response.getIntValue("errcode");
        if (errorCode != 0) {
            throw new XlibaoRuntimeException(errorCode, response.getString("errmsg"));
        }
        // 本次的访问Token
        // String accessToken = response.getString("access_token");
        // 用于刷新的Token
        // String refreshToken = response.getString("refresh_token");
        // 微信在此公众号下的唯一ID
        String openId = response.getString("openid");
        // String unionId = response.getString("unionid");

        response.clear();
        response.put("openId", openId);

        return success(response);
    }
}
