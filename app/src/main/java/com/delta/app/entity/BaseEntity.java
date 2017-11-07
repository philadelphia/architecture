package com.delta.app.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/18 16:44
 */


public class BaseEntity<T>{

    /**
     * code : 0
     * msg : Success
     */

    private String code;
    @SerializedName("message")
    private String msg;
    @SerializedName("rows")
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
