package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/18.
 */

public class VirtualBindingResult {


    /**
     * code : 0
     * msg : Success
     * rows : ok
     */

    private String code;
    private String msg;
    private String rows;

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
