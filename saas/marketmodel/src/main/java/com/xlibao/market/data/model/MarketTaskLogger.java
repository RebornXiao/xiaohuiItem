package com.xlibao.market.data.model;

import java.util.Date;

public class MarketTaskLogger {

    private Long id;
    private Long marketId;
    private String itemLocation;
    private Long executorPassportId;
    private Long itemTemplateId;
    private Integer itemQuantity;
    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation == null ? null : itemLocation.trim();
    }

    public Long getExecutorPassportId() {
        return executorPassportId;
    }

    public void setExecutorPassportId(Long executorPassportId) {
        this.executorPassportId = executorPassportId;
    }

    public Long getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(Long itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}