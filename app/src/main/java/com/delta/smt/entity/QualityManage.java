package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2017/4/18.
 */

public class QualityManage {


    /**
     * code : 0
     * msg : Success
     * rows : [{"quality_id":1,"line":"ST01","slot":"06T021","status":1,"expected_amount":2,"real_amount":0,"duration_time":7707078},{"quality_id":3,"line":"ST01","slot":"06T022","status":1,"expected_amount":2,"real_amount":0,"duration_time":7707078}]
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

    public static class RowsBean extends TimeEntity {
        /**
         * quality_id : 1
         * line : ST01
         * slot : 06T021
         * status : 1
         * expected_amount : 2
         * real_amount : 0
         * duration_time : 7707078
         */

        private int quality_id;
        private String line;
        private String slot;
        private int status;
        private int expected_amount;
        private int real_amount;
        private int duration_time;

        public int getQuality_id() {
            return quality_id;
        }

        public void setQuality_id(int quality_id) {
            this.quality_id = quality_id;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getExpected_amount() {
            return expected_amount;
        }

        public void setExpected_amount(int expected_amount) {
            this.expected_amount = expected_amount;
        }

        public int getReal_amount() {
            return real_amount;
        }

        public void setReal_amount(int real_amount) {
            this.real_amount = real_amount;
        }

        public int getDuration_time() {
            return duration_time;
        }

        public void setDuration_time(int duration_time) {
            this.duration_time = duration_time;
        }
    }
}
