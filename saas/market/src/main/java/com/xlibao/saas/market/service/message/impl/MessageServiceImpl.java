package com.xlibao.saas.market.service.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketEntry;
import com.xlibao.saas.market.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/11.
 */
@Transactional
@Service("messageService")
public class MessageServiceImpl extends BasicWebService implements MessageService {

    @Autowired
    private DataAccessFactory dataAccessFactory;

    @Override
    public JSONObject notifyShipment() {
        return null;
    }

    @Override
    public JSONObject notifyShelvesData() {
        long passportId = getLongParameter("passportId");
        String shelvesData = getUTF("shelvesData");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);
        return  null;
    }
}