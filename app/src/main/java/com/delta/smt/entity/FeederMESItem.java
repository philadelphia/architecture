package com.delta.smt.entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tao.ZT.Zhang on 2016/12/28.
 */

public class FeederMESItem {

    //架位
//    private String position;

    @SerializedName("feeder_id")
    private String feederID;

    @SerializedName("material_no")
    private String materialID;

    //模组ID
    @SerializedName("slot")
    private String slot;

    private int status;

    //流水号
    @SerializedName("serial_no")
    private String serialNumber;

    private boolean isChecked;

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

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "FeederMESItem{" +
                "feederID='" + feederID + '\'' +
                ", materialID='" + materialID + '\'' +
                ", slot='" + slot + '\'' +
                ", status=" + status +
                ", serialNumber='" + serialNumber + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
