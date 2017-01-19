package com.delta.smt.entity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpWarningItem {

    /**
     * code : 0
     * msg : Success
     * rows : [{"line":"3","work_order":"1","face":"A","start_time_plan":"Jan 16, 2017 2:36:43 PM"},{"line":"3","work_order":"2","face":"B","start_time_plan":"Jan 17, 2017 1:56:57 PM"}]
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

    public static class RowsBean extends CountDownEntity {
        /**
         * line : 3
         * work_order : 1
         * face : A
         * start_time_plan : Jan 16, 2017 2:36:43 PM
         */

        @Override
        public Long getCountDownLong() {
            long res = 0;
            if(start_time_plan.length()!=0){
                long getTime = Long.parseLong(date2TimeStamp(start_time_plan, "yyyy-MM-dd HH:mm:ss"));
                long nowTime = Long.parseLong(timeStamp());
                if (nowTime < getTime) {
                    res = getTime - nowTime;
                } else {
                    res = 0;
                }
            }else{
                res = 0;
            }

            return res;
        }

        private String line;
        private String work_order;
        private String face;
        private String start_time_plan;

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getStart_time_plan() {
            return start_time_plan;
        }

        public void setStart_time_plan(String start_time_plan) {
            this.start_time_plan = start_time_plan;
        }

        public String date2TimeStamp(String date_str, String format) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return String.valueOf(sdf.parse(date_str).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public String timeStamp() {
            long time = System.currentTimeMillis();
            String t = String.valueOf(time);
            return t;
        }
    }
}
