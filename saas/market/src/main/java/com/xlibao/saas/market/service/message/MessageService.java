package com.xlibao.saas.market.service.message;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chinahuangxc on 2017/8/11.
 */
public interface MessageService {

    JSONObject notifyShipment();

    JSONObject notifyShelvesData();
}