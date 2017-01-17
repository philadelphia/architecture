package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/16.
 */

public class JsonProductBorrowList {
    private int orderID;

    private String orderName;

    private int orderCount;

    private int orderType;

    private int orderStatus;

    private String model;

    private String compositeMaterial;

    private String pcbMaterial;

    private String side;

    private String lineName;

    private String pcbCode;

    private String planPrdTime;

    private String createTime;

    private String updateTime;

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }
    public int getOrderID(){
        return this.orderID;
    }
    public void setOrderName(String orderName){
        this.orderName = orderName;
    }
    public String getOrderName(){
        return this.orderName;
    }
    public void setOrderCount(int orderCount){
        this.orderCount = orderCount;
    }
    public int getOrderCount(){
        return this.orderCount;
    }
    public void setOrderType(int orderType){
        this.orderType = orderType;
    }
    public int getOrderType(){
        return this.orderType;
    }
    public void setOrderStatus(int orderStatus){
        this.orderStatus = orderStatus;
    }
    public int getOrderStatus(){
        return this.orderStatus;
    }
    public void setModel(String model){
        this.model = model;
    }
    public String getModel(){
        return this.model;
    }
    public void setCompositeMaterial(String compositeMaterial){
        this.compositeMaterial = compositeMaterial;
    }
    public String getCompositeMaterial(){
        return this.compositeMaterial;
    }
    public void setPcbMaterial(String pcbMaterial){
        this.pcbMaterial = pcbMaterial;
    }
    public String getPcbMaterial(){
        return this.pcbMaterial;
    }
    public void setSide(String side){
        this.side = side;
    }
    public String getSide(){
        return this.side;
    }
    public void setLineName(String lineName){
        this.lineName = lineName;
    }
    public String getLineName(){
        return this.lineName;
    }
    public void setPcbCode(String pcbCode){
        this.pcbCode = pcbCode;
    }
    public String getPcbCode(){
        return this.pcbCode;
    }
    public void setPlanPrdTime(String planPrdTime){
        this.planPrdTime = planPrdTime;
    }
    public String getPlanPrdTime(){
        return this.planPrdTime;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }
    public String getUpdateTime(){
        return this.updateTime;
    }

}
