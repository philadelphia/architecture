package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
public class ItemInfo extends CountDownEntity {
    private int id;
    private String text;
    private  long countdown;
    private long endTime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }
}
