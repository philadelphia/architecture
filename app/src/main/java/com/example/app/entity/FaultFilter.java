package com.example.app.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/17 13:44
 */


public class FaultFilter {

    /**
     * code : 0
     * msg : Success
     * rows : [{"main_exception_name":"贴片机故障"},{"main_exception_name":"回焊炉故障"},{"main_exception_name":"AOI故障"},{"main_exception_name":"ICT故障"}]
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
         * main_exception_name : 贴片机故障
         */

        private String main_exception_name;

        public String getMain_exception_name() {
            return main_exception_name;
        }

        public void setMain_exception_name(String main_exception_name) {
            this.main_exception_name = main_exception_name;
        }
    }
}
