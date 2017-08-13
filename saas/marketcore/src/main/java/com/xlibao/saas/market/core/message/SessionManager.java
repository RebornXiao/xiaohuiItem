package com.xlibao.saas.market.core.message;

import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class SessionManager {

    private NettySession hardwareSession;
    private NettySession logicSession;

    private static final SessionManager instance = new SessionManager();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void setHardwareSession(NettySession session) {
        hardwareSession = session;
    }

    public void setLogicSession(NettySession logicSession) {
        this.logicSession = logicSession;
    }

    public void sendHardwareMessage(String content) {
        if (hardwareSession != null) {
            hardwareSession.send(content);
        }
    }

    public void sendLogicSession(MessageOutputStream message) {
        if (logicSession != null) {
            logicSession.send(message);
        }
    }
}