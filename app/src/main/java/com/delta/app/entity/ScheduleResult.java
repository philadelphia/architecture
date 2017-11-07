package com.delta.app.entity;

import com.delta.libs.adapter.TimeEntity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2017/9/27.
 */

public class ScheduleResult extends TimeEntity {

    private String code;
    private String message;
    private List<Schedule> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public List<Schedule> getRows() {
        return rows;
    }

    public void setRows(List<Schedule> rows) {
        this.rows = rows;
    }

    public static class Schedule extends TimeEntity {

        //线别
        private String line_name;
        //工单
        private String work_order;
        //面别
        private String side;
        //剩余时间
        private double remain_time;

        public Schedule(String line_name, String work_order, String side, double remain_time) {
            this.line_name = line_name;
            this.work_order = work_order;
            this.side = side;
            this.remain_time = remain_time;
        }

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public double getRemain_time() {
            return remain_time;
        }

        public void setRemain_time(double remain_time) {
            this.remain_time = remain_time;
        }
    }

}
