package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tao.ZT.Zhang on 2016/12/29.
 */

public class Result <T> {
    @SerializedName("msg")
    private String message;
    private String code;
    private List<T> rows;

    public Result(String code, String message, List<T> rows) {
        this.code = code;
        this.message = message;
        this.rows = rows;
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

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }
}
