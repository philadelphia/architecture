package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */

public class JsonProductToolsVerfyList {

    private int loanStatus;

    private int jigId;

    private String jigcode;

    private String mainBoard;

    private String subBoard;

    private int statId;

    private String statName;

    private int jigTypeId;

    private String jigTypeName;

    public void setLoanStatus(int loanStatus){
        this.loanStatus = loanStatus;
    }
    public int getLoanStatus(){
        return this.loanStatus;
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
    public void setStatId(int statId){
        this.statId = statId;
    }
    public int getStatId(){
        return this.statId;
    }
    public void setStatName(String statName){
        this.statName = statName;
    }
    public String getStatName(){
        return this.statName;
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
        return "JsonProductToolsVerfyList{" +
                "loanStatus=" + loanStatus +
                ", jigId=" + jigId +
                ", jigcode='" + jigcode + '\'' +
                ", mainBoard='" + mainBoard + '\'' +
                ", subBoard='" + subBoard + '\'' +
                ", statId=" + statId +
                ", statName='" + statName + '\'' +
                ", jigTypeId=" + jigTypeId +
                ", jigTypeName='" + jigTypeName + '\'' +
                '}';
    }
}
