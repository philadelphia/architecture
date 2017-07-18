package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/7/4 17:47
 */


public class MantissaBingingCar {

    private String work_order;
    private String side;
    private String car_name;
    private String shelf_name;

    public MantissaBingingCar(String mWork_order, String mSide, String mCar_name, String mShelf_name) {
        work_order = mWork_order;
        side = mSide;
        car_name = mCar_name;
        shelf_name = mShelf_name;
    }

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String mWork_order) {
        work_order = mWork_order;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String mSide) {
        side = mSide;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String mCar_name) {
        car_name = mCar_name;
    }

    public String getShelf_name() {
        return shelf_name;
    }

    public void setShelf_name(String mShelf_name) {
        shelf_name = mShelf_name;
    }
}
