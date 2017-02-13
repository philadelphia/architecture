package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetails {


    public StorageDetails(String material_no, String slot, int amount, int issue_amount) {
        this.material_no = material_no;
        this.slot = slot;
        this.amount = amount;
        this.issue_amount = issue_amount;
    }

    /**
     * material_no : 1511542032
     * slot : N4-10
     * amount : 1000
     * issue_amount : 0
     * shelf_no : D2JD2
     * status : 0
     */

    private String material_no;
    private String slot;
    private int amount;
    private int issue_amount;
    private String shelf_no;
    private int status;

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
