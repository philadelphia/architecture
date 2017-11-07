package com.example.ttsmanager;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/5 9:52
 */


public class TextToSpeechManager implements OnSpeakListener {

    private OnSpeakListener onSpeakListener;
    private boolean isRead = true;

    public void setOnSpeakListener(OnSpeakListener onSpeakListener) {
        this.onSpeakListener = onSpeakListener;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public void init() {
        onSpeakListener.init();
    }

    @Override
    public void readMessage(String message) {

        if (isRead) {

            onSpeakListener.stop();
            onSpeakListener.readMessage(message);
        }
    }

    @Override
    public void stop() {
        onSpeakListener.stop();
    }

    @Override
    public void freeSource() {

        onSpeakListener.freeSource();
    }
}
