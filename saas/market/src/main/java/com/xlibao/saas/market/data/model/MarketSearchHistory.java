package com.xlibao.saas.market.data.model;

import java.util.Date;

public class MarketSearchHistory {

    private Long id;
    private String k;
    private Integer totalSearchTimes;
    private Date lastSearchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    public Integer getTotalSearchTimes() {
        return totalSearchTimes;
    }

    public void setTotalSearchTimes(Integer totalSearchTimes) {
        this.totalSearchTimes = totalSearchTimes;
    }

    public Date getLastSearchTime() {
        return lastSearchTime;
    }

    public void setLastSearchTime(Date lastSearchTime) {
        this.lastSearchTime = lastSearchTime;
    }
}