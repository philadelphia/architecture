package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaWarehouseputBean {

    private String serial_no;
    private String material_no;
    private String unit;
    private String vendor;
    private String dc;
    private String lc;
    private String tc;
    private String po;
    private String qty;


    public MantissaWarehouseputBean(String serial_num, String material_num, String unit, String vendor, String dc, String lc, String trasaction_code, String po, String quantity) {
        this.serial_no = serial_num;
        this.material_no = material_num;
        this.unit = unit;
        this.vendor = vendor;
        this.dc = dc;
        this.lc = lc;
        this.tc = trasaction_code;
        this.po = po;
        this.qty = quantity;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getMaterial_no() {
        return material_no;
    }

    public void setMaterial_no(String material_no) {
        this.material_no = material_no;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
