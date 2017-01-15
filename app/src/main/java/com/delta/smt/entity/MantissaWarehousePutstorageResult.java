package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehousePutstorageResult {
    /**
     * msg : success
     * rows : [{"label":"11111","material_num":"4020108700","serial_num":"12344","shelves":"A1D001","status":1}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehousePutstorage> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MantissaWarehousePutstorage> getrows() {
        return rows;
    }

    public void setrows(List<MantissaWarehousePutstorage> rows) {
        this.rows = rows;
    }


    public static class MantissaWarehousePutstorage {
        /**
         * label : 11111
         * material_num : 4020108700
         * serial_num : 12344
         * shelves : A1D001
         * status : 1
         */

        //标签
        private String label;
        //料号
        private String material_num;
        //流水号
        private String serial_num;
        //架位
        private String shelves;
        //状态
        private String status;

        public MantissaWarehousePutstorage(String label, String material_num, String serial_num, String shelves, String status) {
            this.label = label;
            this.material_num = material_num;
            this.serial_num = serial_num;
            this.shelves = shelves;
            this.status = status;
        }

        public String getLabel() {
            return label;
        }



        public void setLabel(String label) {
            this.label = label;
        }

        public String getMaterial_num() {
            return material_num;
        }

        public void setMaterial_num(String material_num) {
            this.material_num = material_num;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
