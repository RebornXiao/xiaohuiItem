package com.xlibao.saas.market.data.mapper.item;

import com.xlibao.saas.market.data.model.MarketItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketItemMapper {

    List<MarketItem> specialProducts(@Param("marketId") long marketId, @Param("appointType") long appointType, @Param("timeout") long timeout, @Param("sortType") int sortType, @Param("sortValue") int sortValue,
                                     @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> conditionOrdering(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet, @Param("sortType") int sortType, @Param("sortValue") int sortValue,
                                       @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> getItemsForItemTemplateSet(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet, @Param("pageStartIndex") int pageStartIndex, @Param("pageSize") int pageSize);

    List<MarketItem> getItemForItemTemplates(@Param("marketId") long marketId, @Param("itemTemplateSet") String itemTemplateSet);

    List<MarketItem> getItems(@Param("itemSet") String itemSet);
}