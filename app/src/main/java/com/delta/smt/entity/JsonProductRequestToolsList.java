package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/17.
 */

public class JsonProductRequestToolsList {

    private int jigId;

    private String jigcode;

    private int loanStatus;

    private String shelfName;

    private int jigTypeId;

    private String jigTypeName;

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

    @Override
    public String toString() {
        return "JsonProductRequestToolsList{" +
                "jigId=" + jigId +
                ", jigcode='" + jigcode + '\'' +
                ", loanStatus=" + loanStatus +
                ", shelfName='" + shelfName + '\'' +
                ", jigTypeId=" + jigTypeId +
                ", jigTypeName='" + jigTypeName + '\'' +
                '}';
    }
}
