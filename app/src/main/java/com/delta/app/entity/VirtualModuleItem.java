package com.delta.app.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/18.
 */

public class VirtualModuleItem {

    /**
     * code : 0
     * msg : Success
     * rows : ["1","2","3","4","5","6"]
     */

    private String code;
    private String msg;
    private List<String> rows;

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

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }
}
