package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownWarningItem {
    private String lineNumber;
    private String workItemID;
    private String faceID;
    private String status;
    private String rest_time;

    public ModuleDownWarningItem(String faceID, String lineNumber, String rest_time, String status, String workItemID) {
        this.faceID = faceID;
        this.lineNumber = lineNumber;
        this.rest_time = rest_time;
        this.status = status;
        this.workItemID = workItemID;
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

    public String getRest_time() {
        return rest_time;
    }

    public void setRest_time(String rest_time) {
        this.rest_time = rest_time;
    }

    @Override
    public String toString() {
        return "ModuleDownWarningItem{" +
                "faceID='" + faceID + '\'' +
                ", lineNumber=" + lineNumber +
                ", workItemID='" + workItemID + '\'' +
                ", status='" + status + '\'' +
                ", rest_time='" + rest_time + '\'' +
                '}';
    }
}
