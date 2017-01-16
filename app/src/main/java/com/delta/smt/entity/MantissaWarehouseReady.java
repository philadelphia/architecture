package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class MantissaWarehouseReady {


    /**
     * code : 0
     * msg : Success
     * rows : [{"work_order":"20171011","line":"H-01","face":"A","material_num":"3460016900","remain_time":"11:21:27"}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehouse> rows;

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

    public List<MantissaWarehouse> getRows() {
        return rows;
    }

    public void setRows(List<MantissaWarehouse> rows) {
        this.rows = rows;
    }

    public static class MantissaWarehouse {
        /**
         * work_order : 20171011
         * line : H-01
         * face : A
         * material_num : 3460016900
         * remain_time : 11:21:27
         */

        //工单
        private String work_order;
        //产线
        private String line;
        //面别
        private String face;
        //状态
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

    }
}
