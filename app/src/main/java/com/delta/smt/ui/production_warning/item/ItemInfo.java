package com.delta.smt.ui.production_warning.item;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */

public class ItemInfo {
    @SerializedName("type")
    private String title;
    @SerializedName("line")
    private String produceline;
    @SerializedName("message")
    private String info;

    public ItemInfo() {
    }

    public ItemInfo(String title, String produceline, String info) {
        this.title = title;
        this.produceline = produceline;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduceline() {
        return produceline;
    }

    public void setProduceline(String produceline) {
        this.produceline = produceline;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
