package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/5 12:30
 */


public class CountDownEntity  {
    @SerializedName("remain_time")
    protected long countdown;
    protected long endTime;
    protected int id;

    protected  String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
