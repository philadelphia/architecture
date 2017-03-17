package com.delta.smt.entity;

/**
 * Created by Fuxiang.Zhang on 2017/3/15.
 */

public class ItemLocationVerfyList {
    private String barcode;
    private String shelfName;
    private String shelfBarcode;
    private String jigTypeName;
    private String loanStatus;

    public ItemLocationVerfyList() {
    }

    public ItemLocationVerfyList(String barcode, String shelfName, String shelfBarcode, String jigTypeName, String loanStatus) {
        this.barcode = barcode;
        this.shelfName = shelfName;
        this.shelfBarcode = shelfBarcode;
        this.jigTypeName = jigTypeName;
        this.loanStatus = loanStatus;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getShelfBarcode() {
        return shelfBarcode;
    }

    public void setShelfBarcode(String shelfBarcode) {
        this.shelfBarcode = shelfBarcode;
    }

    public String getJigTypeName() {
        return jigTypeName;
    }

    public void setJigTypeName(String jigTypeName) {
        this.jigTypeName = jigTypeName;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}
