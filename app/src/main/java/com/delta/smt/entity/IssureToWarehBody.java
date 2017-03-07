package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/19 19:12
 */


public class IssureToWarehBody {

    /**
     * serial_no : 12343
     * material_no : 4020108700
     * unit : PCS
     * vendor : 10X124
     * dc : 1515
     * lc : daa27g1
     * trasaction_code : 01
     * po :
     * quantity : 100
     */

    private String serial_no;
    private String material_no;
    private String unit;
    private String vendor;
    private String dc;
    private String lc;
    private String trasaction_code;
    private String po;
    private String quantity;
    private String work_order;
    private String part;
    private String side;

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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
