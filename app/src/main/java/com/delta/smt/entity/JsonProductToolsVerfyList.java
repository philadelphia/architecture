package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */

public class JsonProductToolsVerfyList {

    private int loanStatus;

    private int jigID;

    private String barcode;

    private String mainBoard;

    private String subBoard;

    private int statID;

    private String statName;

    private int jigTypeID;

    private String jigTypeName;

    public void setLoanStatus(int loanStatus){
        this.loanStatus = loanStatus;
    }
    public int getLoanStatus(){
        return this.loanStatus;
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
    public void setMainBoard(String mainBoard){
        this.mainBoard = mainBoard;
    }
    public String getMainBoard(){
        return this.mainBoard;
    }
    public void setSubBoard(String subBoard){
        this.subBoard = subBoard;
    }
    public String getSubBoard(){
        return this.subBoard;
    }
    public void setStatID(int statID){
        this.statID = statID;
    }
    public int getStatID(){
        return this.statID;
    }
    public void setStatName(String statName){
        this.statName = statName;
    }
    public String getStatName(){
        return this.statName;
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
