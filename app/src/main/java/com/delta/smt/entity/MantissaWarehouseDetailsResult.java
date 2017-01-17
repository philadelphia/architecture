package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class MantissaWarehouseDetailsResult {


    /**
     * code : 0
     * msg : Success
     * rows : [{"work_order":"20171011","material_num":"4020108400","shelves":"A1D001","re_quantity":"100","se_quantity":"0","status":0},{"work_order":"20171011","material_num":"4020108700","shelves":"A1D001","re_quantity":"10","se_quantity":"0","status":0}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehouseDetails> rows;

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

    public List<MantissaWarehouseDetails> getRows() {
        return rows;
    }

    public void setRows(List<MantissaWarehouseDetails> rows) {
        this.rows = rows;
    }

    public static class MantissaWarehouseDetails {
        /**
         * work_order : 20171011
         * material_num : 4020108400
         * shelves : A1D001
         * re_quantity : 100
         * se_quantity : 0
         * status : 0
         */

        private String work_order;
        private String material_num;
        private String shelves;
        private String re_quantity;
        private String se_quantity;
        private String status;

        public MantissaWarehouseDetails(String work_order, String material_num, String shelves, String re_quantity, String se_quantity, String status) {
            this.work_order = work_order;
            this.material_num = material_num;
            this.shelves = shelves;
            this.re_quantity = re_quantity;
            this.se_quantity = se_quantity;
            this.status = status;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getMaterial_num() {
            return material_num;
        }

        public void setMaterial_num(String material_num) {
            this.material_num = material_num;
        }

        public String getShelves() {
            return shelves;
        }

        public void setShelves(String shelves) {
            this.shelves = shelves;
        }

        public String getRe_quantity() {
            return re_quantity;
        }

        public void setRe_quantity(String re_quantity) {
            this.re_quantity = re_quantity;
        }

        public String getSe_quantity() {
            return se_quantity;
        }

        public void setSe_quantity(String se_quantity) {
            this.se_quantity = se_quantity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
