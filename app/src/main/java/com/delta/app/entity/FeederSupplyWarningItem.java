package com.delta.app.entity;

import com.delta.libs.adapter.TimeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

   public class FeederSupplyWarningItem extends TimeEntity{
    @SerializedName("line_name")
    private String lineName;
    @SerializedName("work_order")
    private String workOrder;
    private String side;
    private int status;
    @SerializedName("remain_time")
    private double remainTime;

    public FeederSupplyWarningItem( String lineName, double remainTime, String side, int status, String workOrder) {
        this.lineName = lineName;
        this.remainTime = remainTime;
        this.side = side;
        this.status = status;
        this.workOrder = workOrder;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public double getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(long remainTime) {
        this.remainTime = remainTime;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    @Override
    public String toString() {
        return "FeederSupplyWarningItem{" +
                "lineName='" + lineName + '\'' +
                ", workOrder='" + workOrder + '\'' +
                ", side='" + side + '\'' +
                ", status='" + status + '\'' +
                ", remainTime=" + remainTime +
                '}';
    }
}
