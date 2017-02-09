package com.delta.smt.entity;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2017/1/18.
 */

public class ResultFeeder {
    /**
     * code : 0
     * msg : Success
     * rows : true
     */

    private String code;
    private String msg;
    private boolean rows;

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

    public void setRows(boolean rows) {
        this.rows = rows;
    }

    public boolean isRows() {
        return rows;
    }
}
