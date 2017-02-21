package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/2/9 9:57
 */


public class StoreEntity {
    private String name;

    private boolean isCheck;

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public StoreEntity() {
    }

    public StoreEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
