package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

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

    public static class RowsBean extends TimeEntity {
        /**
         * line : 3
         * work_order : 1
         * face : A
         * start_time_plan : Jan 16, 2017 2:36:43 PM
         */

        /*@Override
        public Long getCountDownLong() {
            long res = 0;
            if(online_plan_start_time.length()!=0){
                long getTime = Long.parseLong(date2TimeStamp(online_plan_start_time, "yyyy-MM-dd HH:mm:ss"));
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
        }*/

        private String line_name;
        private String work_order;
        private String side;
        private String product_name_main;
        private String product_name;
        private String status;
        private String online_plan_start_time;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getProduct_name_main() {
            return product_name_main;
        }

        public void setProduct_name_main(String product_name_main) {
            this.product_name_main = product_name_main;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getOnline_plan_start_time() {
            return online_plan_start_time;
        }

        public void setOnline_plan_start_time(String online_plan_start_time) {
            this.online_plan_start_time = online_plan_start_time;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
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
