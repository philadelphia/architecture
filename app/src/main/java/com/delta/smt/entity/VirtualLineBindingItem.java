package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingItem {
    /**
     * code : 0
     * msg : Success
     * rows : [{"vitual_id":"0351234708","model_id":"w543"}]
     */

    private String code;
    private String msg;
    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * vitual_id : 0351234708
         * model_id : w543
         */

        private String vitual_id;
        private String model_id;

        public RowsBean(String model_id,String vitual_id ) {
            this.vitual_id = vitual_id;
            this.model_id = model_id;

        }

        public String getVitual_id() {
            return vitual_id;
        }

        public void setVitual_id(String vitual_id) {
            this.vitual_id = vitual_id;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }
    }

    /**
     * code : 0
     * msg : Success
     * rows : ["1","2","3","4","5","6"]
     */

    /*private String code;
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
    }*/


}
