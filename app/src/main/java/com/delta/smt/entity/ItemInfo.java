package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

import java.text.SimpleDateFormat;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
public class ItemInfo extends TimeEntity {
    private String alarminfoId;
    private Long endTime;



    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    private String text;
    private String workNumber;
    private String mainBoard;
    private String subBoard;
    private String side;
    private String machine;
    private String materialNumber;
    private String amount;
    private boolean isAlarminfo;


    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public String getSubBoard() {
        return subBoard;
    }

    public void setSubBoard(String subBoard) {
        this.subBoard = subBoard;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }



    public boolean isAlarminfo() {
        return isAlarminfo;
    }

    public void setAlarminfo(boolean alarminfo) {
        isAlarminfo = alarminfo;
    }


    public String getAlarminfoId() {
        return alarminfoId;
    }

    public void setAlarminfoId(String alarminfoId) {
        this.alarminfoId = alarminfoId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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





    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    @Override
//    public Long getCountDownLong() {
//        //网络请求到的时间
//        long getTime = Long.parseLong(date2TimeStamp(, "yyyy-MM-dd HH:mm:ss"));
//        //现在时间
//        long nowTime = Long.parseLong(timeStamp());
//        if (nowTime >getTime) {
//            res = nowTime-getTime;
//        } else {
//            res = 0;
//        }
//        return res;
//
//    }
    public String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t;
    }

}
