package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutinStorageBean {

    private String material_no;
    private String serial_no;
    private String shelf_no;

    public WarehousePutinStorageBean(String material_no, String serial_no, String shelf_no) {
        this.material_no = material_no;
        this.serial_no = serial_no;
        this.shelf_no = shelf_no;
    }

    public String getMaterial_no() {
        return material_no;
    }

    public void setMaterial_no(String material_no) {
        this.material_no = material_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(String shelf_no) {
        this.shelf_no = shelf_no;
    }
}
