package com.delta.smt.entity;

/**
 * Created by Tao.ZT.Zhang on 2016/12/23.
 */

public class WareHouse {
    private int id ;
    private String name;

    public WareHouse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
