package com.xlibao.saas.market.data.mapper.item;

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
    private MarketItemStockLockLoggerMapper itemStockLockLoggerMapper;
    @Autowired
    private MarketShoppingCartMapper shoppingCartMapper;
    @Autowired
    private MarketSpecialButtonMapper specialButtonMapper;

    public List<MarketSpecialButton> getButtons() {
        return specialButtonMapper.getButtons();
    }
}