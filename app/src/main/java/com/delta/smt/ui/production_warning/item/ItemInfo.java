package com.delta.smt.ui.production_warning.item;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */

public class ItemInfo {
    private String title;
    private String produceline;
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
