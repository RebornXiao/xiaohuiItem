package com.xlibao.saas.market.shop.service.shop.impl;

import com.xlibao.common.BasicWebService;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.saas.market.shop.service.shop.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/9.
 */
@Transactional
@Service("shopService")
public class ShopServiceImpl extends BasicWebService implements ShopService {

    @Override
    public MessageOutputStream securityVerification(MessageInputStream message) {
//        *     <b>username</b> - String 登录用户名(自定义用户名或手机号或其他已绑定的帐号)，必填参数。
//        *     <b>password</b> - String 登录密码，必填参数。
//        *     <b>deviceType</b> - int 设备类型，必填参数；参考：{@linkplain com.xlibao.common.constant.device.DeviceTypeEnum}。
//        *     <b>clientType</b> - int 客户端类型，必填参数；具体参考：{@linkplain com.xlibao.common.constant.passport.ClientTypeEnum}，
//        *                      默认值：{@link com.xlibao.common.constant.passport.ClientTypeEnum#CONSUMER}。
//        *     <b>versionIndex</b> - int 当前的版本标志，必填参数；(内部版本号，一般递增，初始为1，此部分的信息主要由前端决定)。
        return null;
    }
}