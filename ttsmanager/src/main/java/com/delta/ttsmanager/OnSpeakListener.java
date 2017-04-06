package com.delta.ttsmanager;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/5 9:43
 */


public interface OnSpeakListener {

    void init();

    void readMessage(String message);

    void stop();

    void freeSource();

}
