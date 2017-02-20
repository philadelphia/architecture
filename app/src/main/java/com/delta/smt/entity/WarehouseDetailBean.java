package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/16.
 */

public class WarehouseDetailBean {

    public String getWork_order() {
        return work_order;
    }
    private String side;

    public String getSide() {
        return side;
    }

    public WarehouseDetailBean(String side, String workorder) {
        this.side = side;
        this.work_order = workorder;
    }

    public void setSide(String side) {

        this.side = side;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public WarehouseDetailBean(String workorder) {
        this.work_order = workorder;
    }

    private String work_order;

}
