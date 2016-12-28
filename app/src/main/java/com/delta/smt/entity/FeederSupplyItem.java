package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2016/12/28.
 */

public class FeederSupplyItem {
    private String location;
    private String feederID;
    private String materialID;
    private String moduleID;
    private String timeStamp;
    private String status;

    public FeederSupplyItem(String feederID, String location, String materialID, String moduleID, String status) {
        this.feederID = feederID;
        this.location = location;
        this.materialID = materialID;
        this.moduleID = moduleID;
        this.status = status;
    }

    public FeederSupplyItem(String location,String feederID,  String materialID, String moduleID,  String timeStamp,String status) {
        this.feederID = feederID;
        this.location = location;
        this.materialID = materialID;
        this.moduleID = moduleID;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public String getFeederID() {
        return feederID;
    }

    public void setFeederID(String feederID) {
        this.feederID = feederID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
