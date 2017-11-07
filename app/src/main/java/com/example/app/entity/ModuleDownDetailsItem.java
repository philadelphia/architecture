package com.example.app.entity;


/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsItem {

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


        public ModuleDownDetailsItem(String material_no, String serial_no, String feeder_id, String slot, String dest) {
            this.dest = dest;
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


        @Override
        public String toString() {
            return "RowsBean{" +
                    "dest='" + dest + '\'' +
                    ", material_no='" + material_no + '\'' +
                    ", serial_no='" + serial_no + '\'' +
                    ", feeder_id='" + feeder_id + '\'' +
                    ", slot='" + slot + '\'' +
                     +
                    '}';
        }

}
