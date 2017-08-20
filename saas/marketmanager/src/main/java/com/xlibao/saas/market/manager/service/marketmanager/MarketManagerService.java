package com.xlibao.saas.market.manager.service.marketmanager;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.market.data.model.MarketEntry;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface MarketManagerService {

    public JSONObject searchMarkets(MarketEntry entry, int pageSize, int pageIndex);

    public JSONObject getMarket(long id);

}