package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaBingingCarBean {

    private String work_order;
    private String part;
    private String pre_car;

    public MantissaBingingCarBean(String work_order, String part, String pre_car) {
        this.work_order = work_order;
        this.part = part;
        this.pre_car = pre_car;
    }

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPre_car() {
        return pre_car;
    }

    public void setPre_car(String pre_car) {
        this.pre_car = pre_car;
    }
}
