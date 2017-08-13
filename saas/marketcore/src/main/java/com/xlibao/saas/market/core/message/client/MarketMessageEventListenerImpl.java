package com.xlibao.saas.market.core.message.client;

import com.xlibao.common.constant.device.DeviceTypeEnum;
import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.MessageEventListener;
import com.xlibao.io.service.netty.NettyConfig;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.io.service.netty.filter.DefaultMessageDecoder;
import com.xlibao.io.service.netty.filter.DefaultMessageEncoder;
import com.xlibao.market.protocol.ShopProtocol;
import com.xlibao.saas.market.core.service.MessageHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chinahuangxc on 2017/8/12.
 */
public class MarketMessageEventListenerImpl implements MessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MarketMessageEventListenerImpl.class);

    @Override
    public ByteToMessageDecoder newDecoder() {
        return new DefaultMessageDecoder();
    }

    @Override
    public MessageToMessageEncoder newEncoder() {
        return new DefaultMessageEncoder();
    }

    @Override
    public void notifyChannelActive(NettySession session) throws Exception {
        logger.info("【商店业务】作为客户端(与业务服务交互) 建立一个Socket通道 " + session.netTrack());

        // TODO 模拟登录过程 实际应存在页面或客户端操作 这里仅为了测试而编写的内容
        MessageOutputStream message = MessageFactory.createLogicMessage(ShopProtocol.CS_SECURITY_VERIFICATION);
        message.writeUTF("merchant");
        message.writeUTF("123456");
        message.writeInt(DeviceTypeEnum.DEVICE_TYPE_ANDROID.getKey());
        message.writeInt(1);
        session.send(message);
    }

    @Override
    public void notifyMessageReceived(NettySession session, MessageInputStream message) throws Exception {
        // 由消息处理适配器处理消息
        MessageHandlerAdapter.getInstance().marketMessageExecute(session, message);
    }

    @Override
    public void notifySessionClosed(NettySession session) {
    }

    @Override
    public void notifyExceptionCaught(Throwable cause) {
    }

    @Override
    public void notifySessionIdle(NettySession session, int idleType, int idleTimes) {
        if (idleType == NettyConfig.TIME_OUT_BOTH) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}