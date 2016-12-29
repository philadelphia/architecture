package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturn {

    //工单
    private String workOrder;
    //料号
    private String number;
    //流水号
    private String serialNumber;
    //物料架位
    private String location;
    //状态
    private String type;


    public MantissaWarehouseReturn(String workOrder, String number, String serialNumber, String location, String type) {
        this.workOrder = workOrder;
        this.number = number;
        this.serialNumber = serialNumber;
        this.location = location;
        this.type = type;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
