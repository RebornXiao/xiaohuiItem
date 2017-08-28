package com.xlibao.saas.market.service.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.BasicWebService;
import com.xlibao.saas.market.data.DataAccessFactory;
import com.xlibao.saas.market.service.item.ItemService;
import com.xlibao.saas.market.service.manager.MarketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/8/27 0027.
 */
@Transactional
@Service("marketItemService")
public class MarketItemServiceImpl extends BasicWebService implements MarketItemService {

    @Autowired
    private DataAccessFactory dataAccessFactory;

    //按弹夹搜索分页的店铺商品
    public JSONObject searchMarketItems() {
        long marketId = getLongParameter("marketId");
        String searchKey = getUTF("searchKey", null);

        return null;
    }

}
