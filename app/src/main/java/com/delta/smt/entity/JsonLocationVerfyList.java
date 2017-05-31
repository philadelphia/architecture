package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/3/7.
 */

public class JsonLocationVerfyList {

    private int jigId;

    private String jigcode;

    private int statId;

    private int shelfId;

    private String statName;

    private String shelfName;

    private String shelfCode;

    private int jigTypeId;

    private String jigTypeName;

    private int loanStatus;

    public JsonLocationVerfyList(String barcode, String shelfName, String shelfBarcode, String jigTypeName, int loanStatus) {
        this.jigcode = barcode;
        this.shelfName = shelfName;
        this.shelfCode = shelfBarcode;
        this.jigTypeName = jigTypeName;
        this.loanStatus = loanStatus;
    }

    public void setJigId(int jigId){
        this.jigId = jigId;
    }
    public int getJigId(){
        return this.jigId;
    }
    public void setJigcode(String jigcode){
        this.jigcode = jigcode;
    }
    public String getJigcode(){
        return this.jigcode;
    }
    public void setStatId(int statId){
        this.statId = statId;
    }
    public int getStatId(){
        return this.statId;
    }
    public void setShelfId(int shelfId){
        this.shelfId = shelfId;
    }
    public int getShelfId(){
        return this.shelfId;
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
    public void setShelfCode(String shelfCode){
        this.shelfCode = shelfCode;
    }
    public String getShelfCode(){
        return this.shelfCode;
    }
    public void setJigTypeId(int jigTypeId){
        this.jigTypeId = jigTypeId;
    }
    public int getJigTypeId(){
        return this.jigTypeId;
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
