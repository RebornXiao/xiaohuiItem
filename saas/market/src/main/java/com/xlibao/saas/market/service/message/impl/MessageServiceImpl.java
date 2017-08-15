package com.xlibao.saas.market.service.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.saas.market.service.message.MessageService;
import com.xlibao.saas.market.service.order.OrderNotifyTypeEnum;
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
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        String serialNumber = getUTF("serialNumber");

        dataAccessFactory.getOrderDataAccessManager().modifyOrderRemoteStatusLogger(orderSequenceNumber + CommonUtils.SPLIT_UNDER_LINE + serialNumber, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_PAYMENT,
                GlobalAppointmentOptEnum.LOGIC_TRUE.getKey(), System.currentTimeMillis());
        return success();
    }

    @Override
    public JSONObject notifyShelvesData() {
        long passportId = getLongParameter("passportId");
        String shelvesData = getUTF("shelvesData");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);
        return success();
    }
}