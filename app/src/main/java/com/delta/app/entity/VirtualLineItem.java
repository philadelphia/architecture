package com.delta.app.entity;
/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineItem {
        private String virtual_id;
        private String model_id;

        public VirtualLineItem(String model_id,String vitual_id ) {
            this.virtual_id = vitual_id;
            this.model_id = model_id;

        }
        public String getVitual_id() {
            return virtual_id;
        }

        public void setVirtual_id(String virtual_id) {
            this.virtual_id = virtual_id;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

    @Override
    public String toString() {
        return "VirtualLineBindingItem{" +
                "virtual_id='" + virtual_id + '\'' +
                ", model_id='" + model_id + '\'' +
                '}';
    }
}
