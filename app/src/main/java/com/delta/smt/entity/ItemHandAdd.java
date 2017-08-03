package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class ItemHandAdd extends TimeEntity {


    private String title;

    @SerializedName("line")
    private String produce_line;

    @SerializedName("slot")
    private String material_station;


    private int expectedAmount;

    private int realAmount;

    @SerializedName("message")
    private String info;

    @SerializedName("executeTime")
    private String time;

    private int id;

    private int state;


    public ItemHandAdd() {
    }

    public ItemHandAdd(String title, String produce_line, String material_station,
                       int realAmount, String info,String countdown,Long endTime) {
        this.title = title;
        this.produce_line = produce_line;
        this.material_station = material_station;
        this.realAmount = realAmount;
        this.info = info;

    }

/*    public Long getCountDownLong(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date ;
        try {
            date = sdf.parse(time);
            long time = date.getTime();

            Log.i("ItemHandAdd", "time: " + time);
            return  date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(int expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public int getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(int realAmount) {
        this.realAmount = realAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduce_line() {
        return produce_line;
    }

    public void setProduce_line(String produce_line) {
        this.produce_line = produce_line;
    }

    public String getMaterial_station() {
        return material_station;
    }

    public void setMaterial_station(String material_station) {
        this.material_station = material_station;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
