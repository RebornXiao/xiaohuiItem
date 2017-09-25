package com.xlibao.purchase.data.model;

import java.util.Date;

public class PurchaseCommodityStores {
    private Long id;

    private Long warehouseId;

    private Long commodityId;

    private String commodityName;

    private String commodityCode;

    private Integer storesNumber;

    private Integer warnNumber;

    private Date updateTime;

    private Date createTime;

    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    public Integer getStoresNumber() {
        return storesNumber;
    }

    public void setStoresNumber(Integer storesNumber) {
        this.storesNumber = storesNumber;
    }

    public Integer getWarnNumber() {
        return warnNumber;
    }

    public void setWarnNumber(Integer warnNumber) {
        this.warnNumber = warnNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}