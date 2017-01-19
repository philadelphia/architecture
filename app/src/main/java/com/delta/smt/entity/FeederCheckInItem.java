package com.delta.smt.entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String serial_num;

    private String used_time; //已用时间

    @SerializedName("op_time")
    private String checkInTimeStamp;  //入库时间
    private int status;
    private String msg;

    public FeederCheckInItem(String checkInTimeStamp, String feederID, String materialID, String msg, String serial_num, String shelves, int status, String used_time, String workItemID) {
        this.checkInTimeStamp = checkInTimeStamp;
        this.feederID = feederID;
        this.materialID = materialID;
        this.msg = msg;
        this.serial_num = serial_num;
        this.shelves = shelves;
        this.status = status;
        this.used_time = used_time;
        this.workItemID = workItemID;
    }

    public String getCheckInTimeStamp() {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(checkInTimeStamp);
            SimpleDateFormat sdf2  = new SimpleDateFormat("MM/dd HH:mm");
            checkInTimeStamp =sdf2.format(date);
            Log.i("FeederSupplyItem", "getTimeStamp: " + checkInTimeStamp);
            return checkInTimeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
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
                ", serial_num='" + serial_num + '\'' +
                ", used_time='" + used_time + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
