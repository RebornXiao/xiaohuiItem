package com.xlibao.saas.market.core.message;

import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class SessionManager {

    // 硬件的消息会话，建立通道后可直接使用；由硬件的心跳机制来保持链接的有效性
    private NettySession hardwareSession;
    // 商店业务消息会话，必须是通过验证后才能使用；由本进程的心跳机制来保持链接的有效性
    private NettySession marketSession;

    private static final SessionManager instance = new SessionManager();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void setHardwareSession(NettySession session) {
        hardwareSession = session;
    }

    public void setMarketSession(NettySession session) {
        marketSession = session;
    }

    public void sendHardwareMessage(String content) {
        if (hardwareSession == null) {
            // 无法发送，需记录内容后在硬件连接成功时补发
        }
        if (hardwareSession != null) {
            hardwareSession.send(content);
        }
    }

    public void sendMarketSession(MessageOutputStream message) {
        if (marketSession == null) {
            // TODO 发起建立链接的请求
        }
        if (marketSession != null) {
            marketSession.send(message);
        }
    }
}