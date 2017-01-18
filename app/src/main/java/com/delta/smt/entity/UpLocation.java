package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/18.
 */

public class UpLocation {

    private String material_num;
    private String serial_num;
    private String quantity;

    public UpLocation(String material_num, String serial_num, String quantity) {
        this.material_num = material_num;
        this.serial_num = serial_num;
        this.quantity = quantity;
    }

    public String getMaterial_num() {
        return material_num;
    }

    public void setMaterial_num(String material_num) {
        this.material_num = material_num;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
