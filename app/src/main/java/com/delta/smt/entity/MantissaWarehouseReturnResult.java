package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnResult {


    /**
     * code : 0
     * msg : Success
     * rows : [{"shelves":"D33E-01","material_num":"4020108700","work_order":"20171011","status":0},{"shelves":"D33E-02","material_num":"3460016900","work_order":"20171011","status":0},{"shelves":"","material_num":"4020108400","work_order":"20171011","status":0}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehouseReturn> rows;

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

    public List<MantissaWarehouseReturn> getRows() {
        return rows;
    }

    public void setRows(List<MantissaWarehouseReturn> rows) {
        this.rows = rows;
    }

    public static class MantissaWarehouseReturn {
        /**
         * shelves : D33E-01
         * material_num : 4020108700
         * work_order : 20171011
         * status : 0
         */

        private String shelves;
        private String material_num;
        private String serial_num;
        private String work_order;
        private String status;

        public MantissaWarehouseReturn(String shelves, String material_num, String serial_num, String work_order, String status) {
            this.shelves = shelves;
            this.material_num = material_num;
            this.serial_num = serial_num;
            this.work_order = work_order;
            this.status = status;
        }

        public String getSerial_num() {
            return serial_num;
        }

        public void setSerial_num(String serial_num) {
            this.serial_num = serial_num;
        }

        public String getShelves() {
            return shelves;
        }

        public void setShelves(String shelves) {
            this.shelves = shelves;
        }

        public String getMaterial_num() {
            return material_num;
        }

        public void setMaterial_num(String material_num) {
            this.material_num = material_num;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
