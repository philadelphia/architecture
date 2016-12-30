package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class BeginPut {

    //料号
    private String number;
    //流水号
    private String serialNumber;
    //物料架位
    private String location;
    //标签
    private String tag;
    //状态
    private String type;

    public BeginPut(String number, String serialNumber, String location, String tag, String type) {
        this.number = number;
        this.serialNumber = serialNumber;
        this.location = location;
        this.tag = tag;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
