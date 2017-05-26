package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownWarningItem  extends TimeEntity{

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
