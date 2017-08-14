package com.xlibao.saas.market.manager.service.ordermanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.ordermanager.OrderManagerService;
import com.xlibao.saas.market.manager.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("orderManagerService")
public class OrderManagerServiceImpl implements OrderManagerService {

    @Override
    public JSONObject searchPageOrders(long marketId, int orderState, String startTime, String endTime, String searchType, String searchKey, int pageSize, int pageStartIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigFactory.getDomainNameConfig().orderRemoteURL).append("/order/searchPageOrders?")
                .append("marketId=").append(marketId)
                .append("&orderState=").append(orderState)
                .append("&pageSize=").append(pageSize)
                .append("&pageStartIndex=").append(pageStartIndex);

        Utils.append(sb, "startTime", startTime);
        Utils.append(sb, "endTime", endTime);
        Utils.append(sb, "searchType", searchType);
        Utils.append(sb, "searchKey", searchKey);

        String json = HttpRequest.get(sb.toString());
        return JSONObject.parseObject(json);
    }
}