package com.delta.smt.entity;

import com.delta.commonlibs.utils.DeviceUuidFactory;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 18:20
 */


public class SendMessage {

    private String uuid;
    private String type;
    /** 预警消息内容 */
    private Object message;

    public SendMessage( String type) {

        this.type =DeviceUuidFactory.getUuid().toString()+type;
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
