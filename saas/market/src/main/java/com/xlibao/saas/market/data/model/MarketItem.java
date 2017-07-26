package com.xlibao.saas.market.data.model;

import com.xlibao.common.CommonUtils;
import com.xlibao.metadata.item.ItemUnit;
import com.xlibao.saas.market.service.item.DiscountTypeEnum;

import java.util.Date;

public class MarketItem {

    private Long id;
    private Long itemTemplateId;
    private String defineName;
    private String defineImage;
    private Integer defaultSort;
    private Long ownerId;
    private Integer productBatches;
    private String batchesCode;
    private Integer stock;
    private Integer lockStock;
    private Integer pendingQuantity;
    private Integer warningQuantity;
    private Integer keepQuantity;
    private Integer oversoldQuantity;
    private Integer maximumSellCount;
    private Integer minimumSellCount;
    private Integer allocationQuantity;
    private Integer purchaseQuantity;
    private Byte status;
    private Long costPrice;
    private Long sellPrice;
    private Long marketPrice;
    private Long discountPrice;
    private Byte discountType;
    private Integer restrictionQuantity;
    private Byte beyondControl;
    private Byte deliveryDelay;
    private Integer initialSales;
    private Integer actualSales;
    private Long totalStorage;

    private Long totalOutStorage;

    private String description;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(Long itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName == null ? null : defineName.trim();
    }

    public String getDefineImage() {
        return defineImage;
    }

    public void setDefineImage(String defineImage) {
        this.defineImage = defineImage == null ? null : defineImage.trim();
    }

    public Integer getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(Integer defaultSort) {
        this.defaultSort = defaultSort;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(Integer productBatches) {
        this.productBatches = productBatches;
    }

    public String getBatchesCode() {
        return batchesCode;
    }

    public void setBatchesCode(String batchesCode) {
        this.batchesCode = batchesCode == null ? null : batchesCode.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Integer getPendingQuantity() {
        return pendingQuantity;
    }

    public void setPendingQuantity(Integer pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public Integer getWarningQuantity() {
        return warningQuantity;
    }

    public void setWarningQuantity(Integer warningQuantity) {
        this.warningQuantity = warningQuantity;
    }

    public Integer getKeepQuantity() {
        return keepQuantity;
    }

    public void setKeepQuantity(Integer keepQuantity) {
        this.keepQuantity = keepQuantity;
    }

    public Integer getOversoldQuantity() {
        return oversoldQuantity;
    }

    public void setOversoldQuantity(Integer oversoldQuantity) {
        this.oversoldQuantity = oversoldQuantity;
    }

    public Integer getMaximumSellCount() {
        return maximumSellCount;
    }

    public void setMaximumSellCount(Integer maximumSellCount) {
        this.maximumSellCount = maximumSellCount;
    }

    public Integer getMinimumSellCount() {
        return (minimumSellCount == null || minimumSellCount == 0) ? 1 : minimumSellCount;
    }

    public void setMinimumSellCount(Integer minimumSellCount) {
        this.minimumSellCount = minimumSellCount;
    }

    public Integer getAllocationQuantity() {
        return allocationQuantity;
    }

    public void setAllocationQuantity(Integer allocationQuantity) {
        this.allocationQuantity = allocationQuantity;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Byte getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Byte discountType) {
        this.discountType = discountType;
    }

    public Integer getRestrictionQuantity() {
        return restrictionQuantity;
    }

    public void setRestrictionQuantity(Integer restrictionQuantity) {
        this.restrictionQuantity = restrictionQuantity;
    }

    public Byte getBeyondControl() {
        return beyondControl;
    }

    public void setBeyondControl(Byte beyondControl) {
        this.beyondControl = beyondControl;
    }

    public Byte getDeliveryDelay() {
        return deliveryDelay;
    }

    public void setDeliveryDelay(Byte deliveryDelay) {
        this.deliveryDelay = deliveryDelay;
    }

    public Integer getInitialSales() {
        return initialSales;
    }

    public void setInitialSales(Integer initialSales) {
        this.initialSales = initialSales;
    }

    public Integer getActualSales() {
        return actualSales;
    }

    public void setActualSales(Integer actualSales) {
        this.actualSales = actualSales;
    }

    public Long getTotalStorage() {
        return totalStorage;
    }

    public void setTotalStorage(Long totalStorage) {
        this.totalStorage = totalStorage;
    }

    public Long getTotalOutStorage() {
        return totalOutStorage;
    }

    public void setTotalOutStorage(Long totalOutStorage) {
        this.totalOutStorage = totalOutStorage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 当前显示可购买的库存数量 = 真实库存 - 已挂起数量 - 锁定库存 + 超卖库存
     */
    public int getShowStock() {
        return getStock() - getPendingQuantity() - getLockStock() + getOversoldQuantity();
    }

    public long maxPrice() {
        return getMarketPrice() < getSellPrice() ? getSellPrice() : getMarketPrice();
    }

    public String showDiscount(ItemUnit itemUnit) {
        if (discountType == 0 || discountPrice >= sellPrice) {
            return "暂无优惠";
        }
        long maxPrice = sellPrice;
        if (marketPrice > maxPrice) {
            maxPrice = marketPrice;
        }
        float discount = (discountPrice) / (maxPrice * 1.0f);
        DiscountTypeEnum discountTypeEnum = DiscountTypeEnum.getDiscountTypeEnum(discountType);
        String limit = (restrictionQuantity == -1 ? "无限购" : "每天限购" + restrictionQuantity + itemUnit.getTitle());
        return (discountTypeEnum == null ? "" : discountTypeEnum.getValue()) + CommonUtils.formatNumber(discount * 10, "0.00") + "折" + "(" + limit + ")";
    }
}