package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class FeederSupplyWarningItem {
    private int lineNumber;
    private String workItemID;
    private String faceID;
    private String status;

    public FeederSupplyWarningItem(int lineNumber, String workItemID, String faceID, String status) {
        this.lineNumber = lineNumber;
        this.workItemID = workItemID;
        this.faceID = faceID;
        this.status = status;

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

    @Override
    public String toString() {
        return "FeederSupplyWarningItem{" +
                "faceID='" + faceID + '\'' +
                ", lineNumber=" + lineNumber +
                ", workItemID='" + workItemID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
