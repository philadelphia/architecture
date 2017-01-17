package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 18:20
 */


public class SendMessage {
    public SendMessage() {
    }

    int type;
    List<String> content;

    public SendMessage(int type, List<String> content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }
}
