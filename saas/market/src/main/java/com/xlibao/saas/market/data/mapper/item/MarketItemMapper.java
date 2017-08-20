package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.market.data.model.MarketItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketItemMapper {

    int createItem(MarketItem item);

    MarketItem getItem(@Param("marketId") long marketId, @Param("itemTemplateId") long itemTemplateId);

    List<MarketItem> specialProducts(@Param("marketId") long marketId, @Param("appointType") long appointType, @Param("timeout") long timeout, @Param("sortType") int sortType, @Param("sortValue") int sortValue,
                                     @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> conditionOrdering(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet, @Param("sortType") int sortType, @Param("sortValue") int sortValue,
                                       @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> getItemsForItemTemplateSet(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> getItemForItemTemplates(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet);

    List<MarketItem> getItems(@Param("itemSet") String itemSet);

    int lockItemStock(@Param("itemId") long itemId, @Param("lockQuantity") int lockQuantity);

    int itemPending(@Param("itemId") long itemId, @Param("quantity") int quantity);

    int incrementPending(@Param("itemId") long itemId, @Param("quantity") int quantity);

    int decrementItemStock(@Param("itemId") long itemId, @Param("quantity") int quantity);

    int offShelves(@Param("itemId") long itemId, @Param("quantity") int quantity, @Param("status") int status);
}