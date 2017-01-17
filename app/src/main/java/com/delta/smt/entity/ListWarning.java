package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public class ListWarning {
    String pcb;
    String jia;
    String dangqaian;
    String pcbCode;
    String dc;

    public boolean isColor() {
        return isColor;
    }

    public void setColor(boolean color) {
        isColor = color;
    }

    boolean isColor;

    public ListWarning() {
    }

    public ListWarning(String s, String s1, String s2, String s3, String s4) {
    }

    public ListWarning(String pcb, String jia, String dangqaian, String xuqiu, String pcbCode, String dc) {
        this.pcb = pcb;
        this.jia = jia;
        this.dangqaian = dangqaian;
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
