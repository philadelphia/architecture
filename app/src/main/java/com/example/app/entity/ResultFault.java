package com.example.app.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tao.ZT.Zhang on 2016/12/29.
 */

public class ResultFault {
    @SerializedName("msg")
    private String message;
    private String code;


    public ResultFault(String code, String message) {
        this.code = code;
        this.message = message;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                "code='" + code + '\'' +
                ", message='" + message + '\''+
                '}';
    }
}
