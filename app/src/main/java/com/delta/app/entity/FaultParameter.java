package com.delta.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/2/21 13:53
 */


public class FaultParameter {

    /**
     * lines : T12,T13
     * processes : 回焊炉,AOI
     */

    private String lines;
    private String processes;

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getProcesses() {
        return processes;
    }

    public void setProcesses(String processes) {
        this.processes = processes;
    }
}
