package com.xlibao.saas.market.service.item;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chinahuangxc on 2017/7/10.
 */
public interface ItemService {

    JSONObject homepage();

    JSONObject itemTypes();

    JSONObject findSubItemTypes();

    JSONObject findRecommendItems();

    JSONObject pageItems();
}
