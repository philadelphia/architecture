package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tao.ZT.Zhang on 2016/12/29.
 */

public class Result {
    @SerializedName("msg")
    private String message;
    private String data;

    public Result(String data, String message) {
        this.data = data;
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
