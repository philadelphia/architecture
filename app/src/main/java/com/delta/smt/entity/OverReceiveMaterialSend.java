package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/16.
 */

public class OverReceiveMaterialSend {

    /**
     * material_num : 4020108700
     * serial_num : 12344
     * issure_quantity : 2000
     */

    private String material_num;
    private String serial_num;
    private String issure_quantity;

    public OverReceiveMaterialSend(String material_num, String serial_num, String issure_quantity) {
        this.material_num = material_num;

        this.serial_num = serial_num;
        this.issure_quantity = issure_quantity;
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

    public String getIssure_quantity() {
        return issure_quantity;
    }

    public void setIssure_quantity(String issure_quantity) {
        this.issure_quantity = issure_quantity;
    }
}
