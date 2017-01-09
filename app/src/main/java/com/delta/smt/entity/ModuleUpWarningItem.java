package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpWarningItem extends CountDownEntity{
    private String lineNumber;
    private String workItemID;
    private String faceID;
    private String status;

    public ModuleUpWarningItem(String faceID, String lineNumber, String status, String workItemID,Long countdown) {
        this.faceID = faceID;
        this.lineNumber = lineNumber;
        this.status = status;
        this.workItemID = workItemID;
        this.countdown = countdown;
    }

    public String getFaceID() {
        return faceID;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkItemID() {
        return workItemID;
    }

    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID;
    }
}
