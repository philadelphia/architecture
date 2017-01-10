package com.delta.smt.entity;

/**
 *
 */
public class StorageSelect {
    private int id ;
    private String name;

    public StorageSelect(int id, String name) {
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
