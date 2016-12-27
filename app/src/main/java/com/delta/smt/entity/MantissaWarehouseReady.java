package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class MantissaWarehouseReady {

    private String line;
    private String face;
    private String type;
    private String number;
    private String time;

    public MantissaWarehouseReady(String line, String face, String type, String number, String time) {
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
    }
}
