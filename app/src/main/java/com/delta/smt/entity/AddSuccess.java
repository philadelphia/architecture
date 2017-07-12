package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2017/7/6.
 */

public class AddSuccess {

    /**
     * code : 0
     * msg : success
     */

    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
