package com.delta.smt.entity;

import com.delta.commonlibs.utils.DeviceUuidFactory;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 18:20
 */


public class SendMessage {

    private String uuid;
    private int type;
    /** 预警消息内容 */
    private Object message;

    public SendMessage( int type) {
        this.uuid = DeviceUuidFactory.getUuid().toString();
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
