package com.xlibao.saas.market.core.service.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.ShopProtocol;
import com.xlibao.saas.market.core.message.SessionManager;
import com.xlibao.saas.market.core.message.client.HeartbeatCallable;
import com.xlibao.saas.market.core.service.application.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/13.
 */
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Override
    public void logicMessageExecute(NettySession session, MessageInputStream message) {
        short msgId = message.getMsgId();
        if (msgId == ShopProtocol.CS_SECURITY_VERIFICATION) {
            securityVerification(session, message);
        }
    }

    @Override
    public void platformMessageExecute(NettySession session, MessageInputStream message) {
        logger.debug("【平台消息】心跳结果：" + message.readUTF() + "；" + session.netTrack());
    }

    @Override
    public void hardwareMessageExecute(NettySession session, MessageInputStream message) {
        String content = message.readUTF();
        logger.info("【硬件】接收到硬件消息，消息内容：" + content + "；" + session.netTrack());

        SessionManager.getInstance().sendHardwareMessage(content);
    }

    private void securityVerification(NettySession session, MessageInputStream message) {
        byte result = message.readByte();   // 结果标志位，参考：{@link com.xlibao.common.GlobalAppointmentOptEnum#LOGIC_TRUE}、{@link com.xlibao.common.GlobalAppointmentOptEnum#LOGIC_FALSE}

        if (result == GlobalAppointmentOptEnum.LOGIC_FALSE.getKey()) {
            // TODO 登录错误
            logger.error("权限认证失败，错误码：" + message.readInt() + "；错误内容：" + message.readUTF());
            return;
        }
        String msg = message.readUTF();

        JSONObject parameters = JSONObject.parseObject(msg);

        session.setAttribute("passportId", parameters.getLongValue("passportId"));
        SessionManager.getInstance().setLogicSession(session);

        // TODO 启动心跳线程
        new HeartbeatCallable().start();
    }
}