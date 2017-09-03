package com.xlibao.saas.market.service.support.remote;

import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.constant.order.OrderStatusEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.config.ConfigFactory;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.data.model.MarketOrderStatusLogger;
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

    public void shipmentMessage(long passportId, String mark, String content) {
        content = HardwareMessageType.SHIPMENT + content;
        // 记录发送状态
        logger.info("推送出货消息到商店系统，passport id is " + passportId + ", content is " + content);

        final MarketOrderStatusLogger orderStatusLogger = dataAccessFactory.getOrderDataAccessManager().getOrderStatusLogger(mark, OrderStatusEnum.ORDER_STATUS_PAYMENT);
        if (orderStatusLogger != null && orderStatusLogger.getRemoteStatus() == GlobalAppointmentOptEnum.LOGIC_TRUE.getKey()) {
            logger.error("订单重复请求出货功能，mark : " + mark);
            throw new XlibaoRuntimeException("不能重复出货");
        }
        String finalContent = content;
        Runnable runnable = () -> {
            if (orderStatusLogger == null) {
                dataAccessFactory.getOrderDataAccessManager().createOrderStatusLogger(mark, OrderNotifyTypeEnum.HARDWARE.getKey(), OrderStatusEnum.ORDER_STATUS_PAYMENT, GlobalAppointmentOptEnum.LOGIC_FALSE.getKey(), System.currentTimeMillis());
            }
            sendMessage(passportId, finalContent);
        };
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }

    public void shelvesMessage(long passportId, String content) {
        content = HardwareMessageType.SHELVES + content;
        // 记录发送状态
        logger.info("推送货柜消息到商店系统 passport id is " + passportId + ", content is " + content);

        String finalContent = content;
        Runnable runnable = () -> sendMessage(passportId, finalContent);
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }

    public void orderDataMessage(long passportId, String content) {
        content = HardwareMessageType.ORDER + content;
        // 记录发送状态
        logger.info("推送获取订单货柜消息到商店系统 passport id is " + passportId + ", content is " + content);

        String finalContent = content;
        Runnable runnable = () -> sendMessage(passportId, finalContent);
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }

    public void refundMessage(long passportId, String content) {
        content = HardwareMessageType.REFUND + content;
        // 记录发送状态
        logger.info("推送退货消息到商店系统 passport id is " + passportId + ", content is " + content);

        String finalContent = content;
        Runnable runnable = () -> sendMessage(passportId, finalContent);
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }

    private void sendMessage(long passportId, String content) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("partnerId", ConfigFactory.getXMarketConfig().getPartnerId());
        parameters.put("appId", ConfigFactory.getXMarketConfig().getMarketShopAppId());
        parameters.put("passportId", String.valueOf(passportId));
        parameters.put("messageContent", HardwareMessageType.HARDWARE_MSG_START + content + HardwareMessageType.HARDWARE_MSG_END);

        CommonUtils.fillSignature(parameters, ConfigFactory.getXMarketConfig().getMarketShopAppkey());

        String url = ConfigFactory.getDomainNameConfig().marketShopRemoteURL + "marketshop/message/sendHardwarePush.do";
        executor(url, parameters);
    }
}