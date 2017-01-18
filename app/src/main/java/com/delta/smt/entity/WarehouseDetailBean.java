package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/16.
 */

public class WarehouseDetailBean {

    public String getWork_order() {
        return work_order;
    }

    public WarehouseDetailBean(String work_order) {
        this.work_order = work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    private  String  work_order;

}
