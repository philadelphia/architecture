package com.example.app.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/6/16.
 */

public class DebitInputPara {

    /**
     * work_order : 002311703899
     * side : B
     * list : [{"material_no":"0313000001","demand_qty":100,"total_qty":200}]
     */

    private String work_order;
    private String side;
    private List<ListBean> list;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * material_no : 0313000001
         * demand_qty : 100
         * total_qty : 200
         */

        private String material_no;
        private String demand_qty;
        private String total_qty;

        public ListBean(String material_no, String demand_qty, String total_qty) {
            this.material_no = material_no;
            this.demand_qty = demand_qty;
            this.total_qty = total_qty;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getDemand_qty() {
            return demand_qty;
        }

        public void setDemand_qty(String demand_qty) {
            this.demand_qty = demand_qty;
        }

        public String getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(String total_qty) {
            this.total_qty = total_qty;
        }
    }
}
