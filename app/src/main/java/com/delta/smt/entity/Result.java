package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tao.ZT.Zhang on 2016/12/29.
 */

public class Result<T> {
    @SerializedName(value = "msg", alternate = {"message"})
    private String message;
    private int code;
    private List<T> rows;

    public Result(int code, String message, List<T> rows) {
        this.code = code;
        this.message = message;
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
