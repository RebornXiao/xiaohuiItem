package com.xlibao.saas.market.data.model;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.common.CommonUtils;

import java.util.Date;

public class MarketEntry {

    private Long id;
    private Long passportId;
    private String name;
    private Integer type;
    private Integer status;
    private String adminName;
    private String phoneNumber;
    private Long streetId;
    private String province;
    private String city;
    private String district;
    private String street;
    private String streetNumber;
    private String address;
    private String location;
    private Long coveringDistance;
    private Integer deliveryMode;
    private Long deliveryCost;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber == null ? null : streetNumber.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Long getCoveringDistance() {
        return coveringDistance;
    }

    public void setCoveringDistance(Long coveringDistance) {
        this.coveringDistance = coveringDistance;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Long getDeliveryCost() {
        return deliveryCost == null ? 0 : deliveryCost;
    }

    public void setDeliveryCost(Long deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public JSONObject message() {
        JSONObject response = new JSONObject();

        response.put("id", getId());
        response.put("name", getName());
        response.put("type", getType());
        response.put("adminName", CommonUtils.nullToEmpty(getAdminName()));
        response.put("showPhoneNumber", CommonUtils.hideChar(getPhoneNumber(), 4, 7));
        response.put("hidePhoneNumber", getPhoneNumber());
        response.put("formatAddress", CommonUtils.nullToEmpty(getProvince()) + CommonUtils.nullToEmpty(getCity()) + CommonUtils.nullToEmpty(getDistrict()) +
                CommonUtils.nullToEmpty(getStreet()) + CommonUtils.nullToEmpty(getStreetNumber()));
        response.put("address", CommonUtils.nullToEmpty(getAddress()));
        response.put("coveringDistance", CommonUtils.formatDistance(getCoveringDistance()));
        response.put("deliveryMode", getDeliveryMode());
        response.put("deliveryCost", getDeliveryCost());

        return response;
    }
}