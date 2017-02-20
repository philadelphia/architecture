package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownWarningItem {
    /**
     * code : 0
     * msg : Success
     * rows : [{"line":"H3","work_order":"1","side":"A","Product_Name":"A","Product_Name_Main":"A","offline_time":"01-18 12:49"}]
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
         * line : H3
         * work_order : 1
         * side : A
         * Product_Name : A
         * Product_Name_Main : A
         * offline_time : 01-18 12:49
         */

        private String line_name;
        private String work_order;
        private String side;
        private String product_name;
        private String product_name_main;
        private String status;
        private String unplug_mod_actual_finish_time;

        /*@Override
        public Long getCountDownLong() {
            //long time_s = Long.parseLong(unplug_mod_actual_finish_time);
            return 0L;
        }*/

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_name_main() {
            return product_name_main;
        }

        public void setProduct_name_main(String product_name_main) {
            this.product_name_main = product_name_main;
        }

        public String getUnplug_mod_actual_finish_time() {
            return unplug_mod_actual_finish_time;
        }

        public void setUnplug_mod_actual_finish_time(String unplug_mod_actual_finish_time) {
            this.unplug_mod_actual_finish_time = unplug_mod_actual_finish_time;
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


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    /*private String code;
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

        @Override
        public Long getCountDownLong() {
            long res = 0;

            if(end_time != null && end_time.length()>0&&!end_time.equals("-")){
                long getTime = Long.parseLong(date2TimeStamp(end_time, "yyyy-MM-dd HH:mm:ss"));
                long nowTime = Long.parseLong(timeStamp());
                if (nowTime > getTime) {
                    res = nowTime - getTime;
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
        private String end_time;

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

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }


    }*/


}
