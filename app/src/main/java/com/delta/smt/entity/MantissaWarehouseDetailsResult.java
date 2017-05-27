package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class MantissaWarehouseDetailsResult {


    /**
     * code : 0
     * msg : Success
     * rows : [{"id":42,"material_no":"0341299332","slot":"N1-06","amount":7,"issue_amount":0,"shelf_no":"M1T1","status":1},{"id":43,"material_no":"210532000105","slot":"N4-12","amount":8,"issue_amount":0,"shelf_no":"M1T2","status":1}]
     */

    private String code;
    @SerializedName("message")
    private String msg;
    /**
     * id : 42
     * material_no : 0341299332
     * slot : N1-06
     * amount : 7
     * issue_amount : 0
     * shelf_no : M1T1
     * status : 1
     */

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

    public static class RowsBean {
        private int id;
        private String material_no;
        private String slot;
        private int amount;
        private int issue_amount;
        private String shelf_no;
        private int status;

        public RowsBean(int id, String material_no, String slot, int amount) {
            this.id = id;
            this.material_no = material_no;
            this.slot = slot;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getIssue_amount() {
            return issue_amount;
        }

        public void setIssue_amount(int issue_amount) {
            this.issue_amount = issue_amount;
        }

        public String getShelf_no() {
            return shelf_no;
        }

        public void setShelf_no(String shelf_no) {
            this.shelf_no = shelf_no;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
