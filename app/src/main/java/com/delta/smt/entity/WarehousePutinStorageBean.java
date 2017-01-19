package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutinStorageBean {

    private String material_num;
    private String serial_num;
    private String shelves;

    public WarehousePutinStorageBean(String material_num, String serial_num, String shelves) {
        this.material_num = material_num;
        this.serial_num = serial_num;
        this.shelves = shelves;
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

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }
}
