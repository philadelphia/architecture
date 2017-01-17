package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class FeederSupplyWarningItem extends CountDownEntity{
    @SerializedName("line")
    private String lineNumber;
    @SerializedName("work_order")
    private String workItemID;

    @SerializedName("face")
    private String faceID;
    private String status;

    public FeederSupplyWarningItem(String lineNumber, String workItemID, String faceID, String status,String countDown) {
        this.lineNumber = lineNumber;
        this.workItemID = workItemID;
        this.faceID = faceID;
        this.status = status;
        this.countdown = countDown;

    }

    public String getFaceID() {
        return faceID;
    }

    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkItemID() {
        return workItemID;
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID;
    }



}
