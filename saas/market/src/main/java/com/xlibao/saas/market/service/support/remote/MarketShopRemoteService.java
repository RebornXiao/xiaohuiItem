package com.xlibao.saas.market.service.support.remote;

import com.xlibao.common.support.BasicRemoteService;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.market.protocol.HardwareMessageType;
import com.xlibao.saas.market.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/10.
 */
public class MarketShopRemoteService extends BasicRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(MarketShopRemoteService.class);

    public static void sendMessage(long passportId, String content) {
        // 记录发送状态
        logger.info("推送出货消息到商店系统，passport id is " + passportId + ", content is " + content);

        Runnable runnable = () -> {
            Map<String, String> parameters = new HashMap<>();

            parameters.put("passportId", String.valueOf(passportId));
            parameters.put("content", HardwareMessageType.HARDWARE_MSG_START + content + HardwareMessageType.HARDWARE_MSG_END);

            String url = ConfigFactory.getDomainNameConfig().marketShopRemoteURL + "market/shop/message";
            executor(url, parameters);
        };
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }
}