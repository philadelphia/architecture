package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tao.ZT.Zhang on 2016/12/28.
 */

public class FeederSupplyItem {

    //架位
    private String location;

    @SerializedName("feeder_id")
    private String feederID;

    @SerializedName("material_num")
    private String materialID;

    //模组ID
    @SerializedName("slot")
    private String moduleID;
    private int status;

    //上模组时间
    private String timeStamp;

    //流水号
    private String serialNumber;

    public FeederSupplyItem(String feederID,  String materialID, String moduleID, String serialNumber, int status) {
        this.feederID = feederID;
        this.materialID = materialID;
        this.moduleID = moduleID;
        this.serialNumber = serialNumber;
        this.status = status;

    }

    public FeederSupplyItem(String feederID, String location, String materialID, String moduleID, String serialNumber, int status, String timeStamp) {
        this.feederID = feederID;
        this.location = location;
        this.materialID = materialID;
        this.moduleID = moduleID;
        this.serialNumber = serialNumber;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getFeederID() {
        return feederID;
    }

    public void setFeederID(String feederID) {
        this.feederID = feederID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "FeederSupplyItem{" +
                "feederID='" + feederID + '\'' +
                ", location='" + location + '\'' +
                ", materialID='" + materialID + '\'' +
                ", moduleID='" + moduleID + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
