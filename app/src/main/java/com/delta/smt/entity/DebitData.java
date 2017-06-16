package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/6/15 10:56
 */


public class DebitData {


    /**
     * material_no : 4020108300
     * slot : 01T006
     * amount : 5000
     * issue_amount : 3000
     */

    private String material_no;
    private String slot;
    private int amount;
    private int issue_amount;
    private boolean isChecked;

    public void setChecked(boolean mChecked) {
        isChecked = mChecked;
    }

    public boolean isChecked() {
        return isChecked;
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

}
