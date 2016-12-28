package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public class CheckStock {
    String pcb;
    String liu;
    String number;
    String check;
    String zhuangtai;

    public CheckStock() {
    }

    public CheckStock(String pcb, String liu, String number, String check, String zhuangtai) {
        this.pcb = pcb;
        this.liu = liu;
        this.number = number;
        this.check = check;
        this.zhuangtai = zhuangtai;
    }

    public String getPcb() {
        return pcb;
    }

    public void setPcb(String pcb) {
        this.pcb = pcb;
    }

    public String getLiu() {
        return liu;
    }

    public void setLiu(String liu) {
        this.liu = liu;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }
}
