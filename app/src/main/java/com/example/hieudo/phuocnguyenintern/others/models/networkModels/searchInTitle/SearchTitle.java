package com.example.hieudo.phuocnguyenintern.others.models.networkModels.searchInTitle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchTitle {

    @SerializedName("items")
    @Expose
    private List<SearchTitleItem> items = null;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;

    public List<SearchTitleItem> getItems() {
        return items;
    }

    public void setItems(List<SearchTitleItem> items) {
        this.items = items;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

}