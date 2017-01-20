package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/19 19:12
 */


public class IssureToWarehBody {

    /**
     * serial_num : 12343
     * material_num : 4020108700
     * unit : PCS
     * vendor : 10X124
     * dc : 1515
     * lc : daa27g1
     * trasaction_code : 01
     * po :
     * quantity : 100
     */

    private String serial_num;
    private String material_num;
    private String unit;
    private String vendor;
    private String dc;
    private String lc;
    private String trasaction_code;
    private String po;
    private String quantity;

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public String getMaterial_num() {
        return material_num;
    }

    public void setMaterial_num(String material_num) {
        this.material_num = material_num;
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

    public String getTrasaction_code() {
        return trasaction_code;
    }

    public void setTrasaction_code(String trasaction_code) {
        this.trasaction_code = trasaction_code;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
