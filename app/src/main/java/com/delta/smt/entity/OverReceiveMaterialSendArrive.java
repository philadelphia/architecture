package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/16.
 */

public class OverReceiveMaterialSendArrive {

    /**
     * material_num : 4020108700
     * serial_num : 12344
     */

    private String material_num;
    private String serial_num;

    public OverReceiveMaterialSendArrive(String material_num, String serial_num) {
        this.material_num = material_num;
        this.serial_num = serial_num;
    }

    public String getMaterial_num() {
        return material_num;
    }

    public void setMaterial_num(String material_num) {
        this.material_num = material_num;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }
}
