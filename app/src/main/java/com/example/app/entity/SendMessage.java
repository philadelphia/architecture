package com.example.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 18:20
 */


public class SendMessage {

    private String uuid;
    private String type;
    private int regist;//0:subscriber 1:un_subscriber
    /** 预警消息内容 */
    private Object message;

    public int getRegist() {
        return regist;
    }

    public void setRegist(int regist) {
        this.regist = regist;
    }

    public SendMessage(String type) {

        this.type =type;
    }

    public SendMessage(String type, int regist) {
        this.type = type;
        this.regist = regist;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
