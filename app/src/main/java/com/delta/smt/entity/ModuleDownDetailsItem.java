package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsItem {
    private String materialID;
    private String serialID;
    private String feederID;
    private String moduleMaterialStationID;
    private String ownership;
    private String moduleDownTime;

    public ModuleDownDetailsItem(String feederID, String materialID, String moduleDownTime, String moduleMaterialStationID, String ownership, String serialID) {
        this.feederID = feederID;
        this.materialID = materialID;
        this.moduleDownTime = moduleDownTime;
        this.moduleMaterialStationID = moduleMaterialStationID;
        this.ownership = ownership;
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

    public String getModuleDownTime() {
        return moduleDownTime;
    }

    public void setModuleDownTime(String moduleDownTime) {
        this.moduleDownTime = moduleDownTime;
    }

    public String getModuleMaterialStationID() {
        return moduleMaterialStationID;
    }

    public void setModuleMaterialStationID(String moduleMaterialStationID) {
        this.moduleMaterialStationID = moduleMaterialStationID;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }
}
