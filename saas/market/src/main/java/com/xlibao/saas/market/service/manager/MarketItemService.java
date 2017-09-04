package com.xlibao.saas.market.service.manager;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
public interface MarketItemService {

    //按弹夹搜索分页的店铺商品
    JSONObject searchMarketItems();

    JSONObject getMarketItem();

    JSONObject marketItemEditSave();

    JSONObject marketItemAddSave();
}
