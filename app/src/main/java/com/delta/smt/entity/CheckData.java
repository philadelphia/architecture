package com.delta.smt.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/3 17:06
 */


public class CheckData<T> {
    private T t;
    boolean isSeclect;

    public CheckData(T t, boolean isSeclect) {
        this.t = t;
        this.isSeclect = isSeclect;
    }

    public T getT() {
        return t;
    }

    public boolean isSeclect() {
        return isSeclect;
    }

    public void setSeclect(boolean seclect) {
        isSeclect = seclect;
    }
}
