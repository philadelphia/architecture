package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/19 16:03
 */


public class IssureToWarehResult {


    /**
     * msg : success
     * rows : [{"material_num":"1234","shelves":"123","re_quantity":"900","se_quantity":"20","status":0}]
     */

    private String msg;
    /**
     * material_num : 1234
     * shelves : 123
     * re_quantity : 900
     * se_quantity : 20
     * status : 0
     */

    private List<RowsBean> rows;

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
        private String material_num;
        private String shelves;
        private String re_quantity;
        private String se_quantity;
        private String status;

        public RowsBean(String material_num, String shelves, String re_quantity, String se_quantity, String status) {
            this.material_num = material_num;
            this.shelves = shelves;
            this.re_quantity = re_quantity;
            this.se_quantity = se_quantity;
            this.status = status;
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
