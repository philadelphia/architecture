package com.example.app.entity;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public class VirtualModuleID {

    /**
     * code : 0
     * msg : success
     * rows : o1
     */

    private int code;
    private String message;
    private String rows;

    public VirtualModuleID(int code, String message, String rows) {
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

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "VirtualModuleID{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", Rows='" + rows + '\'' +
                '}';
    }
}

