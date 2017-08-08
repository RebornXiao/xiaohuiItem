package com.xlibao.saas.market.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author chinahuangxc on 2017/8/8.
 */
@Configuration
public class SocketServiceConfig {

    @Value("${readOutTime}")
    private String readOutTime = "10";
    @Value("${writeOutTime}")
    private String writeOutTime = "10";
    @Value("${bothOutTime}")
    private String bothOutTime = "15";

    @Value("${servicePort}")
    private String servicePort;


    public String getReadOutTime() {
        return readOutTime;
    }

    public String getWriteOutTime() {
        return writeOutTime;
    }

    public String getBothOutTime() {
        return bothOutTime;
    }

    public String getServicePort() {
        return servicePort;
    }
}