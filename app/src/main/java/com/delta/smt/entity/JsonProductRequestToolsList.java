package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/17.
 */

public class JsonProductRequestToolsList {

    private int jigID;

    private String barcode;

    private int loanStatus;

    private String shelfName;

    private int jigTypeID;

    private String jigTypeName;

    public void setJigID(int jigID){
        this.jigID = jigID;
    }
    public int getJigID(){
        return this.jigID;
    }
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }
    public String getBarcode(){
        return this.barcode;
    }
    public void setLoanStatus(int loanStatus){
        this.loanStatus = loanStatus;
    }
    public int getLoanStatus(){
        return this.loanStatus;
    }
    public void setShelfName(String shelfName){
        this.shelfName = shelfName;
    }
    public String getShelfName(){
        return this.shelfName;
    }
    public void setJigTypeID(int jigTypeID){
        this.jigTypeID = jigTypeID;
    }
    public int getJigTypeID(){
        return this.jigTypeID;
    }
    public void setJigTypeName(String jigTypeName){
        this.jigTypeName = jigTypeName;
    }
    public String getJigTypeName(){
        return this.jigTypeName;
    }

}
