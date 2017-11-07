package com.example.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/19 15:45
 */


public class MaterialCar {


    /**
     * id : 1
     * car_name : SMT-01
     * type : 0
     */

    private int id;
    private String car_name;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
