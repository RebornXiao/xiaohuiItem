package com.xlibao.metadata.item;

import java.util.Date;

public class ItemTemplate {

    private Long id;
    private String name;
    private String defineCode;
    private Long brandId;
    private String barcode;
    private Long typeId;
    private Long unitId;
    private Long costPrice;
    private Long defaultPrice;
    private String imageUrl;
    private String bannerUrl;
    private String introductionPage;
    private Byte status;
    private String description;
    private Long uploadersPassportId;
    private Date uploadTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDefineCode() {
        return defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode == null ? null : defineCode.trim();
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Long defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
    }

    public String getIntroductionPage() {
        return introductionPage;
    }

    public void setIntroductionPage(String introductionPage) {
        this.introductionPage = introductionPage == null ? null : introductionPage.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUploadersPassportId() {
        return uploadersPassportId;
    }

    public void setUploadersPassportId(Long uploadersPassportId) {
        this.uploadersPassportId = uploadersPassportId;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}