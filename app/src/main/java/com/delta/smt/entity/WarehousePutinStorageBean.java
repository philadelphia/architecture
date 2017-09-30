package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutinStorageBean {

    private String material_no;
    private String serial_no;
    private String shelf_no;
    private String work_order;
    private String side;
    private String code;

    public WarehousePutinStorageBean(String material_no, String serial_no, String shelf_no, String work_order, String side, String code) {
        this.material_no = material_no;
        this.serial_no = serial_no;
        this.shelf_no = shelf_no;
        this.work_order = work_order;
        this.side = side;
        this.code = code;
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

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
