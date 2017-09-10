package com.xlibao.saas.market.service.market;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface MarketService {

    JSONObject findMarket();

    JSONObject filterMarket();

    JSONObject showMarkets();

    JSONObject initShelvesDatas();

    JSONObject searchMarkets();

    JSONObject getMarket();

    JSONObject marketResponse();

    JSONObject getAllMarkets();

    JSONObject myFocusMarkets();

    JSONObject marketEditSave();

    JSONObject marketUpdateStatus();
}