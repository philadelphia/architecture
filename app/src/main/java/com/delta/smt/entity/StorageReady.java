package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class StorageReady extends TimeEntity {


    /**
     * line_name : T14
     * work_order : 19617008336
     * side : A
     * status : 0
     * remain_time : -21645
     */

    private String line_name;
    private String work_order;
    private String side;
    private int status;
    private double remain_time = 0L;

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getRemain_time() {
        return remain_time;
    }

    public void setRemain_time(double remain_time) {
        this.remain_time = remain_time;
    }
}
