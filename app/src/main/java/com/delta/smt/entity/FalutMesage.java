package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:25
 */


public class FalutMesage extends CountDownEntity {


    private String produceline;
    private String processing;
    private String faultMessage;
    private String faultCode;


    public FalutMesage(String produceline, String processing, String faultMessage, String faultCode, Long countdown) {
        this.produceline = produceline;
        this.processing = processing;
        this.faultMessage = faultMessage;
        this.faultCode = faultCode;
        this.countdown = countdown;

    }

    public String getProduceline() {
        return produceline;
    }

    public void setProduceline(String produceline) {
        this.produceline = produceline;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }


}
