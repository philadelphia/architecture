package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2017/9/26.
 */

public class AcceptMaterialResult {
    /**
     * total : null
     * message : Success
     * returnTotal : null
     * pageTotal : null
     * pageCurrent : null
     * pageSize : null
     * code : 0
     * rows : 1
     */

    private Object total;
    private String message;
    private Object returnTotal;
    private Object pageTotal;
    private Object pageCurrent;
    private Object pageSize;
    private int code;
    private int rows;

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnTotal() {
        return returnTotal;
    }

    public void setReturnTotal(Object returnTotal) {
        this.returnTotal = returnTotal;
    }

    public Object getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Object pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Object getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Object pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
