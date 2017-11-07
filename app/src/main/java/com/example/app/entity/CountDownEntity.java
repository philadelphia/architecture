package com.example.app.entity;

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
    protected int timeId;
    public long  countDownLong;

    public Long getCountDownLong(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date ;
        try {
            date = sdf.parse(countdown );
            long time = date.getTime();
//            Date dateCu = new Date();
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
    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
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
