package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public class ListWarning {
    String pcb;
    String jia;
    String dangqaian;
    String xuqiu;
    String pcbCode;
    String dc;

    public ListWarning() {
    }

    public ListWarning(String pcb, String jia, String dangqaian, String xuqiu, String pcbCode, String dc) {
        this.pcb = pcb;
        this.jia = jia;
        this.dangqaian = dangqaian;
        this.xuqiu = xuqiu;
        this.pcbCode = pcbCode;
        this.dc = dc;
    }

    public String getPcb() {
        return pcb;
    }

    public void setPcb(String pcb) {
        this.pcb = pcb;
    }

    public String getJia() {
        return jia;
    }

    public void setJia(String jia) {
        this.jia = jia;
    }

    public String getDangqaian() {
        return dangqaian;
    }

    public void setDangqaian(String dangqaian) {
        this.dangqaian = dangqaian;
    }

    public String getXuqiu() {
        return xuqiu;
    }

    public void setXuqiu(String xuqiu) {
        this.xuqiu = xuqiu;
    }

    public String getPcbCode() {
        return pcbCode;
    }

    public void setPcbCode(String pcbCode) {
        this.pcbCode = pcbCode;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

}
