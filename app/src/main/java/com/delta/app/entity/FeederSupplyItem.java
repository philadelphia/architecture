package com.delta.app.entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tao.ZT.Zhang on 2016/12/28.
 */

public class FeederSupplyItem {

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

    //上模组时间
    @SerializedName("bind_time")
    private String bindTime;

    //流水号
    @SerializedName("serial_no")
    private String serialNumber;


    public FeederSupplyItem(){

    }

    public FeederSupplyItem(String bindTime, String feederID, String materialID, String serialNumber, String slot, int status) {
        this.bindTime = bindTime;
        this.feederID = feederID;
        this.materialID = materialID;
        this.serialNumber = serialNumber;
        this.slot = slot;
        this.status = status;
    }

    public String getBindTime() {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(bindTime);
            SimpleDateFormat sdf2  = new SimpleDateFormat("MM/dd HH:mm");
            bindTime =sdf2.format(date);
            Log.i("FeederSupplyActivity", "getBindTime:== " + bindTime);
//            timeStamp = new StringBuilder().append(date.getMonth()).append("/").append(date.getDay()).append(" ").append(date.getHours()).append(":").append(date.getMinutes()).toString();
            Log.i("FeederSupplyItem", "getTimeStamp: " + bindTime);
//            return bindTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
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




    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    @Override
    public String toString() {
        return "FeederSupplyItem{" +
                "bindTime='" + bindTime + '\'' +
                ", feederID='" + feederID + '\'' +
                ", materialID='" + materialID + '\'' +
                ", slot='" + slot + '\'' +
                ", status=" + status +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
