package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveItem {
    private String lineID;
    private String materialID;
    private String shelfPositionID;
    private String demandAmount;
    private String materialRemainingUsageTime;
    private String state;

    public OverReceiveItem(String lineID, String materialID, String shelfPositionID, String demandAmount, String materialRemainingUsageTime, String state) {
        this.demandAmount = demandAmount;
        this.lineID = lineID;
        this.materialID = materialID;
        this.materialRemainingUsageTime = materialRemainingUsageTime;
        this.shelfPositionID = shelfPositionID;
        this.state = state;
    }

    public String getDemandAmount() {
        return demandAmount;
    }

    public void setDemandAmount(String demandAmount) {
        this.demandAmount = demandAmount;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialRemainingUsageTime() {
        return materialRemainingUsageTime;
    }

    public void setMaterialRemainingUsageTime(String materialRemainingUsageTime) {
        this.materialRemainingUsageTime = materialRemainingUsageTime;
    }

    public String getShelfPositionID() {
        return shelfPositionID;
    }

    public void setShelfPositionID(String shelfPositionID) {
        this.shelfPositionID = shelfPositionID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
