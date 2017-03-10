package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 14:19
 */


public class WarningMessage<T>  {
    /**
     * type : 9
     * message : [{"work_order_id":0,"line_name":"T07","work_order":"2311701218/2316","side":"B","product_name_main":"DPS-495BB A","product_name":"DPS-495BB A","online_plan_start_time":"","status":204}]
     */

    private int type;
    private T message;

    public WarningMessage(int type, T message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

}
