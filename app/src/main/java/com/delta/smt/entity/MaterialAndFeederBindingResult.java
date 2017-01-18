package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/17.
 */

public class MaterialAndFeederBindingResult {

    /**
     * code : 0
     * msg : Success
     * rows : 1
     */

    private String code;
    private String msg;
    private int rows;

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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
