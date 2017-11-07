package com.delta.app.entity.bindrequest;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 16:07
 * @description :
 */

public class BindMaterialBean {
    private String serial_no;
    private String material_no;
    private String dc;
    private String lc;
    private String po;
    private String qty;
    private String unit;
    private String vendor;
    private String tc;
    private String inv_no;

    public BindMaterialBean() {
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

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getInv_no() {
        return inv_no;
    }

    public void setInv_no(String inv_no) {
        this.inv_no = inv_no;
    }

    public BindMaterialBean(String serial_no, String material_no, String dc, String lc, String po, String qty, String unit, String vendor, String tc, String inv_no) {
        this.serial_no = serial_no;
        this.material_no = material_no;
        this.dc = dc;
        this.lc = lc;
        this.po = po;
        this.qty = qty;
        this.unit = unit;
        this.vendor = vendor;
        this.tc = tc;
        this.inv_no = inv_no;
    }
}
