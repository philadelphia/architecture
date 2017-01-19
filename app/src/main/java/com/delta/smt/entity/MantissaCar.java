package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaCar {

    private String code;
    private String msg;
    private String rows;


    public MantissaCar(String code, String msg, String rows) {
        this.code = code;
        this.msg = msg;
        this.rows = rows;
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

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
