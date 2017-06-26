package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/6/20 16:07
 */


public class UpLoadEntity {

    /**
     * is_feeder_buffer : 0
     * feeding_list : [{"material_no":"1552458032","serial_no":"D201702241521454001","feeder_id":"KT8BD30662","slot":"02T07"}]
     * material_list : [{"material_no":"1552458032","serial_no":"D201702241521454003","slot":"03T08"}]
     */

    private int is_feeder_buffer;
    private List<FeedingListBean> feeding_list;
    private List<MaterialListBean> material_list;
    private int currentStep;

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int mCurrentStep) {
        currentStep = mCurrentStep;
    }

    public int getIs_feeder_buffer() {
        return is_feeder_buffer;
    }

    public void setIs_feeder_buffer(int is_feeder_buffer) {
        this.is_feeder_buffer = is_feeder_buffer;
    }

    public List<FeedingListBean> getFeeding_list() {
        return feeding_list;
    }

    public void setFeeding_list(List<FeedingListBean> feeding_list) {
        this.feeding_list = feeding_list;
    }

    public List<MaterialListBean> getMaterial_list() {
        return material_list;
    }

    public void setMaterial_list(List<MaterialListBean> material_list) {
        this.material_list = material_list;
    }

    public static class FeedingListBean {
        /**
         * material_no : 1552458032
         * serial_no : D201702241521454001
         * feeder_id : KT8BD30662
         * slot : 02T07
         */

        private String material_no;
        private String serial_no;
        private String feeder_id;
        private String slot;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean mChecked) {
            isChecked = mChecked;
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
    }

    public static class MaterialListBean {
        /**
         * material_no : 1552458032
         * serial_no : D201702241521454003
         * slot : 03T08
         */

        private String material_no;
        private String serial_no;
        private String slot;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean mChecked) {
            isChecked = mChecked;
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

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }
    }

}
