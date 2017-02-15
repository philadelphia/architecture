package com.delta.smt.ui.production_warning.item;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class ItemAcceptMaterialDetail {

    private String material_id;
    private String material_station;
    private String remain;
    private String unit;
    private String location;

    public ItemAcceptMaterialDetail() {
    }

    public ItemAcceptMaterialDetail(String material_id, String material_station, String remain, String unit, String location) {
        this.material_id = material_id;
        this.material_station = material_station;
        this.remain = remain;
        this.unit = unit;
        this.location = location;
    }


    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_station() {
        return material_station;
    }

    public void setMaterial_station(String material_station) {
        this.material_station = material_station;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String  remain) {
        this.remain = remain;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
