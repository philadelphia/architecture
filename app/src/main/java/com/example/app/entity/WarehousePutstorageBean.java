package com.example.app.entity;

/**
 * Created by Zhenyu.Liu on 2017/1/17.
 */

public class WarehousePutstorageBean {

    //标签
    private String moveLabel;

    public String getMoveLabel() {
        return moveLabel;
    }

    public void setMoveLabel(String moveLabel) {
        this.moveLabel = moveLabel;
    }

    public WarehousePutstorageBean(String moveLabel) {

        this.moveLabel = moveLabel;
    }
}
