package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/18.
 */

public class UpLocation {

    private String material_no;
    private String serial_num;

    public UpLocation(String material_no, String serial_num) {
        this.material_no = material_no;
        this.serial_num = serial_num;
    }

    public String getMaterial_no() {
        return material_no;
    }

    public void setMaterial_no(String material_no) {
        this.material_no = material_no;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }
}
