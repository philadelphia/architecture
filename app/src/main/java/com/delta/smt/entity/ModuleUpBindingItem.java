package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingItem {
    private String materialID;
    private String serialID;
    private String feederID;
    private String moduleMaterialStationID;
    private String moduleUpTime;

    public ModuleUpBindingItem(String feederID, String materialID, String moduleMaterialStationID, String moduleUpTime, String serialID) {
        this.feederID = feederID;
        this.materialID = materialID;
        this.moduleMaterialStationID = moduleMaterialStationID;
        this.moduleUpTime = moduleUpTime;
        this.serialID = serialID;
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

    public String getModuleMaterialStationID() {
        return moduleMaterialStationID;
    }

    public void setModuleMaterialStationID(String moduleMaterialStationID) {
        this.moduleMaterialStationID = moduleMaterialStationID;
    }

    public String getModuleUpTime() {
        return moduleUpTime;
    }

    public void setModuleUpTime(String moduleUpTime) {
        this.moduleUpTime = moduleUpTime;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    @Override
    public String toString() {
        return "ModuleUpBindingItem{" +
                "feederID='" + feederID + '\'' +
                ", materialID='" + materialID + '\'' +
                ", serialID='" + serialID + '\'' +
                ", moduleMaterialStationID='" + moduleMaterialStationID + '\'' +
                ", moduleUpTime='" + moduleUpTime + '\'' +
                '}';
    }
}
