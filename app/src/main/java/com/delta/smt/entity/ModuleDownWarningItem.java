package com.delta.smt.entity;

import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownWarningItem {

    /**
     * code : 0
     * msg : Success
     * rows : [{"line":"H3","work_order":"1","face":"A","end_time":"Jan 9, 2017 2:36:53 PM"}]
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
         * line : H3
         * work_order : 1
         * face : A
         * end_time : Jan 9, 2017 2:36:53 PM
         */

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
