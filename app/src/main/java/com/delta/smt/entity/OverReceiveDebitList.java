package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveDebitList {

    /**
     * code : 0
     * message : Success
     * rows : [{"id":57702,"line_name":"T07","slot":"01T006","material_no":"4020108300","amount":3000,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"A1D001"},{"id":57702,"line_name":"T07","slot":"01T011","material_no":"0341299332","amount":2000,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"D1NC3"},{"id":57702,"line_name":"T07","slot":"04T012","material_no":"210532000105","amount":1500,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"D3IB2"}]
     */

    private String code;
    private String message;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 57702
         * line_name : T07
         * slot : 01T006
         * material_no : 4020108300
         * amount : 3000
         * issue_amount : 0
         * status : 4
         * remain_time : 900
         * shelf_no : A1D001
         */

        private String work_order;
        private String side;
        private String material_no;
        private String issue_amount;
        private boolean isChecked;

        public RowsBean(String work_order, String side, String material_no, String issue_amount, boolean isChecked) {
            this.work_order = work_order;
            this.side = side;
            this.material_no = material_no;
            this.issue_amount = issue_amount;
            this.isChecked = isChecked;
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

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getIssue_amount() {
            return issue_amount;
        }

        public void setIssue_amount(String issue_amount) {
            this.issue_amount = issue_amount;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
