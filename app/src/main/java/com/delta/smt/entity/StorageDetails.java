package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetails {

    //料号
    @SerializedName("material_num")
    private String number;

    //料号架位
    @SerializedName("shelves")
    private String location;

    //需求量
    @SerializedName("re_quantity")
    private String needNumber;

    //发料量
    @SerializedName("se_quantity")
    private String shipments;

    //状态
    @SerializedName("status")
    private String type;

    public StorageDetails(String number, String location, String needNumber, String shipments, String type) {
        this.number = number;
        this.location = location;
        this.needNumber = needNumber;
        this.shipments = shipments;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNeedNumber() {
        return needNumber;
    }

    public void setNeedNumber(String needNumber) {
        this.needNumber = needNumber;
    }

    public String getShipments() {
        return shipments;
    }

    public void setShipments(String shipments) {
        this.shipments = shipments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
