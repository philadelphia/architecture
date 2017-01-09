package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class FeederSupplyWarningItem extends CountDownEntity{
    private int lineNumber;
    private String workItemID;
    private String faceID;
    private String status;

    public FeederSupplyWarningItem(int lineNumber, String workItemID, String faceID, String status,long countDown) {
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
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
