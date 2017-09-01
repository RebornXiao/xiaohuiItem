package com.xlibao.saas.market.core.service.hardware.impl;

import com.xlibao.common.CommonUtils;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.core.service.hardware.HardwareService;
import com.xlibao.saas.market.core.service.support.MarketApplicationRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author chinahuangxc on 2017/8/13.
 */
@Service("hardwareService")
public class HardwareServiceImpl implements HardwareService {

    private static final Logger logger = LoggerFactory.getLogger(HardwareServiceImpl.class);

    @Override
    public void fromHardwareMessageExecute(NettySession session, String content) {
        content = content.replaceAll(CommonUtils.SPACE, "");
        // 消息类型
        String messageType = content.substring(4, 8);

        logger.info("【接收到来自硬件的消息】Message type is " + messageType + "; content " + content + "; " + session.netTrack());
        // 正式消息内容(过滤了消息起始符、消息类型、消息结束符)
        if (HardwareMessageType.WARN.equals(messageType)) { // 心跳或其他警告消息
            return;
        }
        content = content.substring(8);
        switch (messageType) {
            case HardwareMessageType.SHIPMENT:
                MarketApplicationRemoteService.notifyShipment(content.substring(0, 16), content.substring(16, 20));
                break;
            case HardwareMessageType.SHELVES:
                MarketApplicationRemoteService.notifyShelvesData((Long) session.getAttribute("passportId"), content);
                break;
            case HardwareMessageType.ORDER:
                break;
            case HardwareMessageType.REFUND:
                break;
            case HardwareMessageType.PICK_UP:
                break;
        }
    }
}