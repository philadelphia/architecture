package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutstorageBean {

    private String material_num;
    private String serial_num;
    private String label;

    public WarehousePutstorageBean(String material_num, String serial_num, String label) {
        this.material_num = material_num;
        this.serial_num = serial_num;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
