package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
public class ItemInfo extends CountDownEntity {
    private String text;
    private String workNumber;
    private String machine;
    private String materialNumber;

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
}
