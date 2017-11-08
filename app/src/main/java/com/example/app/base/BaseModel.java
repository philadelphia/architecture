package com.example.app.base;



public class BaseModel<T> {


    private T t;

    public BaseModel(T t) {
        this.t = t;
    }

    public T getService() {
        return t;
    }
}
