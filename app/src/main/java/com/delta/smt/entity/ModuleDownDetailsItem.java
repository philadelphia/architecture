package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsItem {

    /**
     * code : 0
     * msg : Success
     * rows : [{"material_no":"4020246000","serial_no":" D201611261521452449 ","feeder_id":"KT8BD30662","slot":"03T028","dest":"Feeder缓冲区","unbind_time":1200}]
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
         * material_no : 4020246000
         * serial_no :  D201611261521452449
         * feeder_id : KT8BD30662
         * slot : 03T028
         * dest : Feeder缓冲区
         * unbind_time : 1200
         */

        private String material_no;
        private String serial_no;
        private String feeder_id;
        private String slot;
        private String dest;
        private String unbind_time;

        public RowsBean(String material_no,String serial_no,   String feeder_id, String slot,String dest, String unbind_time) {
            this.dest = dest;
            this.unbind_time = unbind_time;
            this.slot = slot;
            this.serial_no = serial_no;
            this.material_no = material_no;
            this.feeder_id = feeder_id;
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

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public String getUnbind_time() {
            return unbind_time;
        }

        public void setUnbind_time(String unbind_time) {
            this.unbind_time = unbind_time;
        }
    }

    @Override
    public String toString() {
        return "ModuleDownDetailsItem{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", rows=" + rows +
                '}';
    }
}
