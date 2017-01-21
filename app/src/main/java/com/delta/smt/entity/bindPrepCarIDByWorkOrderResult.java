package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/19 15:59
 */


public class BindPrepCarIDByWorkOrderResult {

    /**
     * msg : success
     * data : [{"material_num":"0351234701","shelves":"D33E02-01","demand":"1000","amount":"2000","status":0}]
     */

    private String msg;
    /**
     * material_num : 0351234701
     * shelves : D33E02-01
     * demand : 1000
     * amount : 2000
     * status : 0
     */

    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String material_num;
        private String shelves;
        private String demand;
        private String amount;
        private String msg;
        private int status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMaterial_num() {
            return material_num;
        }

        public void setMaterial_num(String material_num) {
            this.material_num = material_num;
        }

        public String getShelves() {
            return shelves;
        }

        public void setShelves(String shelves) {
            this.shelves = shelves;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
