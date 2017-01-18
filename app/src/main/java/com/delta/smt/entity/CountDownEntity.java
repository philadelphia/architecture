package com.delta.smt.entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/5 12:30
 */


public class CountDownEntity  {
    @SerializedName("remain_time")
    protected String countdown;
    protected long endTime;
    protected int id;
    public long  countDownLong;

    public Long getCountDownLong(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date ;
        try {
            date = sdf.parse(countdown);
            long time = date.getTime();
            Log.i("SupplyFragment", "time: " + time);
            return  date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCountDownLong(long countDownLong){
        this.countDownLong = countDownLong;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
