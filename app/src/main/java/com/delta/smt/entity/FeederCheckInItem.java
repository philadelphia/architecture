package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/28.
 */

public class FeederCheckInItem {
    @SerializedName("work_order")
    private String workItemID;

    @SerializedName("feeder_id")
    private String feederID;

    @SerializedName("material_num")
    private String materialID;

    private String shelves;


    private String used_time; //已用时间

    @SerializedName("op_time")
    private String checkInTimeStamp;  //入库时间
    private int status;

    public FeederCheckInItem(String checkInTimeStamp, String feederID, String materialID, String shelves, int status, String used_time, String workItemID) {
        this.checkInTimeStamp = checkInTimeStamp;
        this.feederID = feederID;
        this.materialID = materialID;
        this.shelves = shelves;
        this.status = status;
        this.used_time = used_time;
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

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsed_time() {
        return used_time;
    }

    public void setUsed_time(String used_time) {
        this.used_time = used_time;
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
                ", used_time='" + used_time + '\'' +
                ", status=" + status +
                '}';
    }
}
