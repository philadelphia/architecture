package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 18:20
 */


public class SendMessage {
    int type;

    public SendMessage() {
    }

    public SendMessage(int type) {

        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
