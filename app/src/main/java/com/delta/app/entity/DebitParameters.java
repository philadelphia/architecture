package com.delta.app.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/6/15 18:19
 */


public class DebitParameters {


    /**
     * work_order : 002311703899
     * side : B
     * rfname : 124133
     * action : 000
     * list : [{"slot":"1","material_no":"0313000001","demand_qty":100,"total_qty":200}]
     */

    private String work_order;
    private String side;
    private String rfname;
    private String action;
    private String part;

    public String getPart() {
        return part;
    }

    public void setPart(String mPart) {
        part = mPart;
    }

    private List<ListBean> list;

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

    public String getRfname() {
        return rfname;
    }

    public void setRfname(String rfname) {
        this.rfname = rfname;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * slot : 1
         * material_no : 0313000001
         * demand_qty : 100
         * total_qty : 200
         */

        private String slot;
        private String material_no;
        private int demand_qty;
        private int total_qty;

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public int getDemand_qty() {
            return demand_qty;
        }

        public void setDemand_qty(int demand_qty) {
            this.demand_qty = demand_qty;
        }

        public int getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(int total_qty) {
            this.total_qty = total_qty;
        }
    }
}
