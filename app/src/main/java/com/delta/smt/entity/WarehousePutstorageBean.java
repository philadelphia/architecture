package com.delta.smt.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutstorageBean {

    //尾数仓架位
    private String shelf_no;
    //标签
    private String label_name;

    public WarehousePutstorageBean(String shelf_no, String label_name) {
        this.shelf_no = shelf_no;
        this.label_name = label_name;
    }

    public String getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(String shelf_no) {
        this.shelf_no = shelf_no;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }
}
