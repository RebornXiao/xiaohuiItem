package com.xlibao.saas.market.shop.service.shop.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.CommonUtils;
import com.xlibao.common.GlobalAppointmentOptEnum;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.support.PassportRemoteService;
import com.xlibao.io.entry.MessageFactory;
import com.xlibao.io.entry.MessageInputStream;
import com.xlibao.io.entry.MessageOutputStream;
import com.xlibao.io.service.netty.NettySession;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.shop.service.MarketSessionManager;
import com.xlibao.saas.market.shop.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/8/9.
 */
@Transactional
@Service("shopService")
public class ShopServiceImpl extends BasicWebService implements ShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private MarketSessionManager marketSessionManager;

    @Override
    public MessageOutputStream securityVerification(MessageInputStream message, NettySession session) {
        String username = message.readUTF();
        String password = message.readUTF();
        int deviceType = message.readInt();
        int versionIndex = message.readInt();

        MessageOutputStream output = MessageFactory.createResponseMessage(message);
        try {
            JSONObject response = PassportRemoteService.loginPassport(username, password, deviceType, versionIndex);

            JSONObject passportData = response.getJSONObject("response");

            marketSessionManager.put(passportData.getLong("passportId"), session);

            output.writeByte(GlobalAppointmentOptEnum.LOGIC_TRUE.getKey());
            output.writeUTF(response.toString());
        } catch (Exception ex) {
            output.writeByte(GlobalAppointmentOptEnum.LOGIC_FALSE.getKey());
            if (ex instanceof XlibaoRuntimeException) {
                XlibaoRuntimeException xlibaoRuntimeException = (XlibaoRuntimeException) ex;

                output.writeInt(xlibaoRuntimeException.getCode());
                output.writeUTF(xlibaoRuntimeException.getMessage());
            } else {
                output.writeInt(FAIL_CODE);
                output.writeUTF(FAIL_MSG);
            }
        }
        return output;
    }

    @Override
    public void hardwareMessage(MessageInputStream message, NettySession session) {
        String hardwareMessage = message.readUTF();

        logger.info("硬件消息内容：" + hardwareMessage + "；" + session.netTrack());

        hardwareMessage = hardwareMessage.replaceAll("AAAA", "");

        String messageType = hardwareMessage.substring(0, 4);

        if (HardwareMessageType.SHIPMENT.equals(messageType)) {
            // 出货结果
        }
        if (HardwareMessageType.SHELVES.equals(messageType)) {
            // 货架信息
        }
        if (HardwareMessageType.ORDER.equals(messageType)) {
            // 订单信息
        }
        if (HardwareMessageType.RETURN.equals(messageType)) {
            // 退货
        }
        if (HardwareMessageType.PICK_UP.equals(messageType)) {
            // 取货
        }
        if (HardwareMessageType.WARN.equals(messageType)) {
            // 警告
        }
    }

    public static void main(String[] args) {
        String message = "AAAA 0001 12345678 0001 05010301 0006 05010302 0004 05010303 0007 05010304 0002 0004 **** FFFF";

        System.out.println(message.replaceAll(CommonUtils.SPACE, ""));

        message = message.replaceAll("AAAA", "").replaceAll(CommonUtils.SPACE, "");

        String messageType = message.substring(0, 4);

        System.out.println(message.substring(4, message.length() - 8));

        System.out.println(messageType);

        String c = Integer.toHexString(16);
        int q = 4 - c.length();
        for (int i = 0; i < q; i++) { // 补零
            c = "0" + c;
        }
        System.out.println(c);
    }
}