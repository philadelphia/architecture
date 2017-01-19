package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaCarBean {

    private String work_order;
    private String part;

    public MantissaCarBean(String work_order, String part) {
        this.work_order = work_order;
        this.part = part;
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
}
