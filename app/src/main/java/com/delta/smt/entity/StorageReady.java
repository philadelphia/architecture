package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class StorageReady extends CountDownEntity {
    /**
     * work_order : 20171011
     * line : H-01
     * face : A
     * remain_time : 10:11:09
     * status : 1
     */

    private String work_order;
    private String line;
    private String face;
    private int status;

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


/*    private String line;
    private String face;
    @SerializedName("status")
    private String type;
    @SerializedName("work_order")
    private String number;
    @SerializedName("remain_time")
    private String time;

    public StorageReady(String line, String face, String type, String number, String time) {
        this.line = line;
        this.face = face;
        this.type = type;
        this.number = number;
        this.time = time;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/
}
