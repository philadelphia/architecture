package com.delta.app.entity;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2017/6/16.
 */

public class ModuleDownDebit {
    /**
     * material_no : 4020108300
     * serial_no : D201702241521450008
     */

    private String material_no;
    private String serial_no;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;



    public String getMaterial_no() {
        return material_no;
    }

    public void setMaterial_no(String material_no) {
        this.material_no = material_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    @Override
    public String toString() {
        return "ModuleDownDebit{" +
                "material_no='" + material_no + '\'' +
                ", serial_no='" + serial_no + '\'' +
                '}';
    }
}
