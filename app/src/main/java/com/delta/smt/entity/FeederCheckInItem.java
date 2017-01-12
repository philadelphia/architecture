package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2016/12/28.
 */

public class FeederCheckInItem {
    private String workItemID;
    private String feederID;
    private String materialID;
    private String shelves;
    private String checkInTimeStamp;
    private String status;


    public FeederCheckInItem(String workItemID,String feederID,  String materialID, String shelves, String status) {
        this.feederID = feederID;
        this.shelves = shelves;
        this.materialID = materialID;
        this.status = status;
        this.workItemID = workItemID;
    }

    public FeederCheckInItem(String workItemID,String feederID,  String materialID, String shelves, String checkInTimeStamp,String status) {
        this.checkInTimeStamp = checkInTimeStamp;
        this.feederID = feederID;
        this.shelves = shelves;
        this.materialID = materialID;
        this.status = status;
        this.workItemID = workItemID;
    }


    public String getCheckInTimeStamp() {
        return checkInTimeStamp;
    }

    public void setCheckInTimeStamp(String checkInTimeStamp) {
        this.checkInTimeStamp = checkInTimeStamp;
    }

    public String getFeederID() {
        return feederID;
    }

    public void setFeederID(String feederID) {
        this.feederID = feederID;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkItemID() {
        return workItemID;
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID;
    }

    @Override
    public String toString() {
        return "FeederCheckInItem{" +
                "checkInTimeStamp='" + checkInTimeStamp + '\'' +
                ", workItemID='" + workItemID + '\'' +
                ", feederID='" + feederID + '\'' +
                ", materialID='" + materialID + '\'' +
                ", shelves='" + shelves + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
