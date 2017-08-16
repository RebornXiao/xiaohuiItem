package com.xlibao.saas.market.manager.service.marketmanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.common.http.HttpRequest;
import com.xlibao.market.data.model.MarketEntry;
import com.xlibao.saas.market.manager.config.ConfigFactory;
import com.xlibao.saas.market.manager.service.marketmanager.MarketManagerService;
import com.xlibao.saas.market.manager.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinahuangxc on 2017/7/10.
 */
@Transactional
@Service("marketManagerService")
public class MarketManagerServiceImpl implements MarketManagerService {

    public JSONObject searchMarkets(MarketEntry entry, int pageSize, int pageIndex) {

        StringBuilder sb = new StringBuilder();
        Map map = new HashMap<>();
        Utils.appendStr(sb, "province", entry.getProvince());
        Utils.appendStr(sb, "city", entry.getCity());
        Utils.appendStr(sb, "district", entry.getDistrict());
        Utils.appendStr(sb, "street", entry.getStreet());
        Utils.appendLong(sb, "streetId", entry.getStreetId());
        Utils.appendStr(sb, "streetNumber", entry.getStreetNumber());
        Utils.appendInt(sb, "type", entry.getType());
        Utils.appendInt(sb, "status", entry.getStatus());
        Utils.appendInt(sb, "deliveryMode", entry.getDeliveryMode());
        Utils.appendInt(sb, "pageSize", pageSize);
        Utils.appendInt(sb, "pageIndex", pageIndex);

        String json = HttpRequest.post(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/searchMarkets.do?" + sb.toString(), map);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }


    public JSONObject getMarket(long id) {
        String json = HttpRequest.get(ConfigFactory.getDomainNameConfig().marketRemoteURL + "/marketmanager/getMarket.do?id=" + id);
        JSONObject response = JSONObject.parseObject(json);
        return response;
    }

}