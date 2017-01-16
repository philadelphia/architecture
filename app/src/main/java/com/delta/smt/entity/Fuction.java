package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/15 16:17
 */
public class Fuction {
    String title;
    int id;

    public Fuction(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
