package com.xlibao.saas.market.service.support.remote;

import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.metadata.order.OrderEntry;
import com.xlibao.saas.market.config.ConfigFactory;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.order.OrderNotifyTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/10.
 */
@Transactional
@Component
public class MarketShopRemoteService extends BasicRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(MarketShopRemoteService.class);

    @Autowired
    private DataAccessFactory dataAccessFactory;

    public void sendMessage(long passportId, OrderEntry orderEntry, String content) {
        // 记录发送状态
        logger.info("推送出货消息到商店系统，passport id is " + passportId + ", content is " + content);

        Runnable runnable = () -> {
            dataAccessFactory.getOrderDataAccessManager().createOrderStatusLogger(orderEntry.getOrderSequenceNumber(), OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_PAYMENT,
                    GlobalAppointmentOptEnum.LOGIC_FALSE.getKey(), System.currentTimeMillis());

            Map<String, String> parameters = new HashMap<>();
            // PassportRemoteService.signatureParameters();
            parameters.put("partnerId", ConfigFactory.getXMarketConfig().getPartnerId());
            parameters.put("appId", ConfigFactory.getXMarketConfig().getMarketShopAppId());
            parameters.put("passportId", String.valueOf(passportId));
            parameters.put("messageContent", HardwareMessageType.HARDWARE_MSG_START + content + HardwareMessageType.HARDWARE_MSG_END);

            CommonUtils.fillSignature(parameters, ConfigFactory.getXMarketConfig().getMarketShopAppkey());

            String url = ConfigFactory.getDomainNameConfig().marketShopRemoteURL + "market/shop/message/sendHardwarePush.do";
            executor(url, parameters);
        };
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }
}