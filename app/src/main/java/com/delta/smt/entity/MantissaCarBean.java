package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaCarBean {

    private String work_order;
    private String part;
    private String side;

    public MantissaCarBean(String work_order, String part) {
        this.work_order = work_order;
        this.part = part;
    }

    public MantissaCarBean(String workorder, String part, String side) {
        this.work_order = workorder;
        this.part = part;
        this.side = side;
    }

    public void setSide(String side) {

        this.side = side;
    }

    public String getSide() {
        return side;
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
