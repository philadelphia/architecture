package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpWarningItem extends CountDownEntity{
    private String lineNumber;
    private String workItemID;
    private String faceID;
    private String status;
    //private String rest_time;

    public ModuleUpWarningItem(String faceID, String lineNumber, String status, String workItemID,Long countdown) {
        this.faceID = faceID;
        this.lineNumber = lineNumber;
        //this.rest_time = rest_time;
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

    /*public String getRest_time() {
        return rest_time;
    }

    public void setRest_time(String rest_time) {
        this.rest_time = rest_time;
    }*/

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID;
    }
}
