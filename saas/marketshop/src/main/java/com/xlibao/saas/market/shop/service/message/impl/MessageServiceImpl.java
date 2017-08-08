package com.xlibao.saas.market.shop.service.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.shop.service.message.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/8.
 */
@Transactional
@Service("messageService")
public class MessageServiceImpl extends BasicWebService implements MessageService {

    @Override
    public JSONObject sendPush() {
        int type = getIntParameter("type", 0);

        String messageContent = getUTF("messageContent");
        return null;
    }
}