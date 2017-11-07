package com.example.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/15 16:17
 */
public class Function {
    String title;
    int id;

    public Function(String title, int id) {
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
