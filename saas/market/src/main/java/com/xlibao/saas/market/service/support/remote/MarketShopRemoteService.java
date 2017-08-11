package com.xlibao.saas.market.service.support.remote;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.exception.XlibaoRuntimeException;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.common.thread.AsyncScheduledService;
import com.xlibao.saas.market.config.ConfigFactory;
import com.xlibao.saas.market.service.XMarketServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/8/10.
 */
public class MarketShopRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(MarketShopRemoteService.class);

    public static void sendMessage(long passportId, String content) {
        // 记录发送状态
        logger.info("推送出货消息到商店系统，passport id is " + passportId + ", content is " + content);

        Runnable runnable = () -> {
            Map<String, String> parameters = new HashMap<>();

            // TODO content 需要CRC算法加上标志位
            parameters.put("passportId", String.valueOf(passportId));
            parameters.put("content", XMarketServiceConfig.HARDWARE_MSG_START + content + XMarketServiceConfig.HARDWARE_MSG_END);

            String url = ConfigFactory.getDomainNameConfig().marketShopRemoteURL + "market/shop/message";
            try {
                String data = HttpRequest.post(url, parameters);

                JSONObject response = JSONObject.parseObject(data);
                int code = response.getIntValue("code");
                if (code != 0) {
                    throw new XlibaoRuntimeException(code, response.getString("msg"));
                }
            } catch (Exception ex) {
                logger.error("执行" + url + "操作发生异常，请求参数为：" + parameters + "，异常信息：" + ex.getMessage(), ex);
            }
        };
        // 执行即时任务
        AsyncScheduledService.submitImmediateRemoteNotifyTask(runnable);
    }
}