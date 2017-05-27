package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/16.
 */

public class JsonProductBorrowList {

    private int id;

    private String orderName;

    private int orderCount;

    private int orderType;

    private int orderStatus;

    private String mainBoard;

    private String subBoard;

    private String compositeMaterial;

    private String pcbMaterial;

    private String side;

    private String lineName;

    private String pcbCode;

    private String planPrdTime;

    private String createTime;

    private String updateTime;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
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

    @Override
    public String toString() {
        return "JsonProductBorrowList{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", orderCount=" + orderCount +
                ", orderType=" + orderType +
                ", orderStatus=" + orderStatus +
                ", mainBoard='" + mainBoard + '\'' +
                ", subBoard='" + subBoard + '\'' +
                ", compositeMaterial='" + compositeMaterial + '\'' +
                ", pcbMaterial='" + pcbMaterial + '\'' +
                ", side='" + side + '\'' +
                ", lineName='" + lineName + '\'' +
                ", pcbCode='" + pcbCode + '\'' +
                ", planPrdTime='" + planPrdTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
