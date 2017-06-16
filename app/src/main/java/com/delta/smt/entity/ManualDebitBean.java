package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2017/6/16.
 */

public class ManualDebitBean {

    private String code;
    private String message;
    private List<ManualDebit> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public List<ManualDebit> getRows() {
        return rows;
    }

    public void setRows(List<ManualDebit> rows) {
        this.rows = rows;
    }


    public static class ManualDebit {

        private String serial_no;
        private String material_no;
        private boolean isChecked;

        public ManualDebit(String serial_no, String material_no, boolean isChecked) {
            this.serial_no = serial_no;
            this.material_no = material_no;
            this.isChecked = isChecked;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}