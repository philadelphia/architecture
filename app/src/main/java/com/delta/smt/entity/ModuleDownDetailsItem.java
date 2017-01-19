package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsItem {

    /**
     * code : 0
     * msg : Success
     * rows : [{"id":1,"material_num":"4020108700","serial_num":"2016082500","feeder_id":"562632","slot":"03T0128","belong":"Feeder缓冲区","end_time":""},{"id":2,"material_num":"4020112600","serial_num":"2016082501","feeder_id":"KT8BD30661","slot":"03T0229","belong":"尾数仓","end_time":""},{"id":3,"material_num":"0341236301","serial_num":"2016082502","feeder_id":"KT8BD30663","slot":"03T0330","end_time":""}]
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
         * id : 1
         * material_num : 4020108700
         * serial_num : 2016082500
         * feeder_id : 562632
         * slot : 03T0128
         * belong : Feeder缓冲区
         * end_time :
         */

        private int id;
        private String material_num;
        private String serial_num;
        private String feeder_id;
        private String slot;
        private String belong;
        private String end_time;

        public RowsBean(int id, String material_num, String serial_num, String feeder_id, String slot, String belong, String end_time) {
            this.belong = belong;
            this.end_time = end_time;
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

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
