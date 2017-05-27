package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpWarningItem  extends TimeEntity {

    /**
     * work_order_id : 0
     * line_name : T14
     * work_order : 2311701702

     * side : A
     * product_name_main : DPS-650AB-14 C
     * product_name : DC-3633
     * online_plan_start_time : NULL
     * status : 204
     */

    @SerializedName("work_order_id")
    private int workOrderID;
    @SerializedName("line_name")
    private String lineName;
    @SerializedName("work_order")
    private String workOrder;
    private String side;
    @SerializedName("product_name_main")
    private String productNameMain;
    @SerializedName("product_name")
    private String productName;

    @SerializedName("online_plan_start_time")
    private String onlinePlanStartTime;
    private int status;

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getProductNameMain() {
        return productNameMain;
    }

    public void setProductNameMain(String productNameMain) {
        this.productNameMain = productNameMain;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOnlinePlanStartTime() {
        return onlinePlanStartTime;
    }

    public void setOnlinePlanStartTime(String onlinePlanStartTime) {
        this.onlinePlanStartTime = onlinePlanStartTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ModuleUpWarningItem{" +
                "workOrderID=" + workOrderID +
                ", lineName='" + lineName + '\'' +
                ", workOrder='" + workOrder + '\'' +
                ", side='" + side + '\'' +
                ", productNameMain='" + productNameMain + '\'' +
                ", productName='" + productName + '\'' +
                ", onlinePlanStartTime='" + onlinePlanStartTime + '\'' +
                ", status=" + status +
                '}';
    }
}
