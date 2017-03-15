package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/3/7.
 */

public class JsonLocationVerfyList {

    private int jigID;

    private String barcode;

    private int statID;

    private int shelfID;

    private String statName;

    private String shelfName;

    private String shelfBarcode;

    private int jigTypeID;

    private String jigTypeName;

    private int loanStatus;

    public JsonLocationVerfyList(String barcode, String shelfName, String shelfBarcode, String jigTypeName, int loanStatus) {
        this.barcode = barcode;
        this.shelfName = shelfName;
        this.shelfBarcode = shelfBarcode;
        this.jigTypeName = jigTypeName;
        this.loanStatus = loanStatus;
    }

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
    public void setStatID(int statID){
        this.statID = statID;
    }
    public int getStatID(){
        return this.statID;
    }
    public void setShelfID(int shelfID){
        this.shelfID = shelfID;
    }
    public int getShelfID(){
        return this.shelfID;
    }
    public void setStatName(String statName){
        this.statName = statName;
    }
    public String getStatName(){
        return this.statName;
    }
    public void setShelfName(String shelfName){
        this.shelfName = shelfName;
    }
    public String getShelfName(){
        return this.shelfName;
    }
    public void setShelfBarcode(String shelfBarcode){
        this.shelfBarcode = shelfBarcode;
    }
    public String getShelfBarcode(){
        return this.shelfBarcode;
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
    public void setLoanStatus(int loanStatus){
        this.loanStatus = loanStatus;
    }
    public int getLoanStatus(){
        return this.loanStatus;
    }

}
