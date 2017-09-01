package com.xlibao.saas.market.service.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.constant.order.DeliverTypeEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketCoreMessageLogger;
import com.xlibao.saas.market.service.market.ShelvesService;
import com.xlibao.saas.market.service.message.MessageService;
import com.xlibao.saas.market.service.order.OrderNotifyTypeEnum;
import com.xlibao.saas.market.service.support.remote.OrderRemoteService;
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
    @Autowired
    private ShelvesService shelvesService;

    @Override
    public JSONObject notifyShipment() {
        String orderSequenceNumber = getUTF("orderSequenceNumber");
        String serialNumber = getUTF("serialNumber");

        OrderEntry orderEntry = OrderRemoteService.getOrder(orderSequenceNumber);
        if (orderEntry.getDeliverType() == DeliverTypeEnum.PICKED_UP.getKey()) {
            // 修改订单状态
            OrderRemoteService.distributionOrder(orderEntry.getId(), OrderStatusEnum.ORDER_STATUS_DISTRIBUTION.getKey(), GlobalAppointmentOptEnum.LOGIC_FALSE.getKey());
        }
        dataAccessFactory.getOrderDataAccessManager().modifyOrderRemoteStatusLogger(orderSequenceNumber + CommonUtils.SPLIT_UNDER_LINE + serialNumber, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_PAYMENT,
                GlobalAppointmentOptEnum.LOGIC_TRUE.getKey(), System.currentTimeMillis());
        return success();
    }

    @Override
    public JSONObject notifyShelvesData() {
        long passportId = getLongParameter("passportId");
        String content = getUTF("content");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);

        shelvesService.builderShelvesData(marketEntry, content);
        return success();
    }

    @Override
    public JSONObject askOrderPickUp() {
        long passportId = getLongParameter("passportId");
        String orderSequenceNumber = getUTF("orderSequenceNumber");

        MarketEntry marketEntry = dataAccessFactory.getMarketDataCacheService().getMarketForPassport(passportId);

        String keyword = HardwareMessageType.PICK_UP + CommonUtils.SPLIT_UNDER_LINE + orderSequenceNumber;
        MarketCoreMessageLogger coreMessageLogger = dataAccessFactory.getMessageDataAccessManager().getMessageLogger(marketEntry.getId(), keyword);
        if (coreMessageLogger == null) {
            coreMessageLogger = new MarketCoreMessageLogger();
            coreMessageLogger.setMarketId(marketEntry.getId());
            coreMessageLogger.setKeyword(keyword);
            coreMessageLogger.setOriginIp(getHttpServletRequest().getLocalAddr() + CommonUtils.SPACE + getHttpServletRequest().getLocalName());
            coreMessageLogger.setLaunchStatus((int) GlobalAppointmentOptEnum.LOGIC_TRUE.getKey());
            // TODO 忘记是出于什么用意了 先置空
            coreMessageLogger.setTargetMark("");
            coreMessageLogger.setNeedCallback(GlobalAppointmentOptEnum.LOGIC_TRUE.getKey());
            dataAccessFactory.getMessageDataAccessManager().createMessageLogger(coreMessageLogger);

            return success();
        }
        if (coreMessageLogger.getCallbackStatus() == GlobalAppointmentOptEnum.LOGIC_TRUE.getKey()) {
            return fail("该订单的出货请求已处理，不能再次出货");
        }
        return success();
    }
}