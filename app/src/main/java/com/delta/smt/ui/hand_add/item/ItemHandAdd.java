package com.delta.smt.ui.hand_add.item;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class ItemHandAdd {
    private String title;
    private String produce_line;
    private String material_station;
    private String add_count;
    private String info;

    public ItemHandAdd() {
    }

    public ItemHandAdd(String title, String produce_line, String material_station, String add_count, String info) {
        this.title = title;
        this.produce_line = produce_line;
        this.material_station = material_station;
        this.add_count = add_count;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduce_line() {
        return produce_line;
    }

    public void setProduce_line(String produce_line) {
        this.produce_line = produce_line;
    }

    public String getMaterial_station() {
        return material_station;
    }

    public void setMaterial_station(String material_station) {
        this.material_station = material_station;
    }

    public String getAdd_count() {
        return add_count;
    }

    public void setAdd_count(String add_count) {
        this.add_count = add_count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
