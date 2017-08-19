package com.xlibao.saas.market.core.service.hardware.impl;

import com.xlibao.common.CommonUtils;
import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.market.protocol.ShopProtocol;
import com.xlibao.saas.market.core.message.SessionManager;
import com.xlibao.saas.market.core.service.hardware.HardwareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chinahuangxc on 2017/8/13.
 */
@Service("hardwareService")
public class HardwareServiceImpl implements HardwareService {

    private static final Logger logger = LoggerFactory.getLogger(HardwareServiceImpl.class);

    @Autowired
    private SessionManager sessionManager;

    @Override
    public void fromHardwareMessageExecute(NettySession session, String content) {
        content = content.replaceAll(CommonUtils.SPACE, "");
        // 消息类型
        String messageType = content.substring(4, 8);

        // 接收到硬件回调时使用
        // update market_core_message_logger set callback_time = #{callbackTime}, callback_status = #{callbackStatus} where keyword = #{keyword} and target_mark = #{targetMark}

        logger.info("【接收到来自硬件的消息】Message type is " + messageType + "; content " + content + "; " + session.netTrack());
        // 正式消息内容(过滤了消息起始符、消息类型、消息结束符)
        content = content.substring(8);
        if (HardwareMessageType.WARN.equals(messageType)) { // 心跳或其他警告消息
            return;
        }
        // TODO 记录接收的状态，避免消息重复处理

        // 将消息发送给商店业务处理
        MessageOutputStream message = MessageFactory.createInternalMessage(ShopProtocol.CS_HARDWARE);
        message.writeUTF(messageType);
        message.writeUTF(content);

        // 通知业务时使用
        // update market_core_message_logger set notify_time = #{notifyTime} where keyword = #{keyword} and target_mark = #{targetMark}
        sessionManager.sendMarketSession(message);
    }
}