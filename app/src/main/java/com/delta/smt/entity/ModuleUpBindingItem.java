package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingItem {

    /**
     * code : 0
     * msg : Success
     * rows : [{"id":3,"part_id":"0351234709","feeder_id":"","create_time":""},{"id":1,"part_id":"0351234707","serial_num":"5432","feeder_id":"562632","solt":"03T028","create_time":"2017-01-17 13:17:23"},{"id":2,"part_id":"0351234708","serial_num":"w543","feeder_id":"feeder2","solt":"03T028","create_time":"2017-01-18 12:49:58"}]
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
         * work_order : 2016
         * material_no : 111111111
         * serial_no : 222
         * feeder_id : 1
         * slot : 03T01
         * qty : 0
         * id : 0
         */

        private String work_order;
        private String material_no;
        private String serial_no;
        private String feeder_id;
        private String slot;
        private String qty;
        private String id;

        public RowsBean(String work_order, String material_no, String serial_no, String feeder_id, String slot, String qty, String id) {
            this.feeder_id = feeder_id;
            this.work_order = work_order;
            this.slot = slot;
            this.serial_no = serial_no;
            this.qty = qty;
            this.material_no = material_no;
            this.id = id;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getFeeder_id() {
            return feeder_id;
        }

        public void setFeeder_id(String feeder_id) {
            this.feeder_id = feeder_id;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        /*private int id;
        private String material_num;
        private String serial_num;
        private String feeder_id;
        private String slot;
        private String create_time;

        public RowsBean(int id, String material_num,String serial_num, String feeder_id,  String slot, String create_time) {
            this.create_time = create_time;
            this.feeder_id = feeder_id;
            this.id = id;
            this.material_num = material_num;
            this.serial_num = serial_num;
            this.slot = slot;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFeeder_id() {
            return feeder_id;
        }

        public void setFeeder_id(String feeder_id) {
            this.feeder_id = feeder_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSerial_num() {
            return serial_num;
        }

        public void setSerial_num(String serial_num) {
            this.serial_num = serial_num;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getMaterial_num() {
            return material_num;
        }

        public void setMaterial_num(String material_num) {
            this.material_num = material_num;
        }*/
    }
}
