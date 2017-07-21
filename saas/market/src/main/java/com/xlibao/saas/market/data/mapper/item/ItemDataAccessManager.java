package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.common.CommonUtils;
import com.xlibao.saas.market.data.model.MarketItem;
import com.xlibao.saas.market.data.model.MarketItemDailyPurchaseLogger;
import com.xlibao.saas.market.data.model.MarketSpecialButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chinahuangxc on 2017/7/15.
 */
@Transactional
@Component
public class ItemDataAccessManager {

    @Autowired
    private MarketItemMapper itemMapper;
    @Autowired
    private MarketItemLocationMapper itemLocationMapper;
    @Autowired
    private MarketItemDailyPurchaseLoggerMapper itemDailyPurchaseLoggerMapper;
    @Autowired
    private MarketItemStockLockLoggerMapper itemStockLockLoggerMapper;
    @Autowired
    private MarketShoppingCartMapper shoppingCartMapper;
    @Autowired
    private MarketSpecialButtonMapper specialButtonMapper;

    public List<MarketItem> specialProducts(long marketId, long appointType, long timeout, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        return itemMapper.specialProducts(marketId, appointType, timeout, sortType, sortValue, pageStartIndex, pageSize);
    }

    public List<MarketItem> conditionOrdering(long marketId, String itemTemplateSet, int sortType, int sortValue, int pageStartIndex, int pageSize) {
        return itemMapper.conditionOrdering(marketId, itemTemplateSet, sortType, sortValue, pageStartIndex, pageSize);
    }

    public List<MarketSpecialButton> getButtons() {
        return specialButtonMapper.getButtons();
    }

    public List<MarketItemDailyPurchaseLogger> passportDailyBuyLoggers(long passportId, String itemSet) {
        return itemDailyPurchaseLoggerMapper.passportDailyBuyLoggers(passportId, itemSet, CommonUtils.todayForCH());
    }
}