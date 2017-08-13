package com.xlibao.saas.market.core.message.client;

import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.saas.market.core.config.ServerConfig;
import com.xlibao.saas.market.core.message.SessionManager;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class HeartbeatCallable extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * ServerConfig.getBothTimeout());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            MessageOutputStream message = MessageFactory.createPlatformMessage(MessageFactory.MSG_ID_HEARTBEAT);
            message.writeUTF("商店心跳信息");

            SessionManager.getInstance().sendLogicSession(message);
        }
    }
}
