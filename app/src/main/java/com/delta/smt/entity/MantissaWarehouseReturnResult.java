package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnResult {


    /**
     * code : 0
     * msg : Success
     * rows : [{"shelves":"D33E-01","material_num":"4020108700","work_order":"20171011","status":0},{"shelves":"D33E-02","material_num":"3460016900","work_order":"20171011","status":0},{"shelves":"","material_num":"4020108400","work_order":"20171011","status":0}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehouseReturn> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MantissaWarehouseReturn> getRows() {
        return rows;
    }

    public void setRows(List<MantissaWarehouseReturn> rows) {
        this.rows = rows;
    }

    public static class MantissaWarehouseReturn {

        private String material_no;
        private String serial_no;
        private String shelf_no;

        public MantissaWarehouseReturn(String material_no, String serial_no, String shelf_no) {
            this.material_no = material_no;
            this.serial_no = serial_no;
            this.shelf_no = shelf_no;
        }

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

        public String getShelf_no() {
            return shelf_no;
        }

        public void setShelf_no(String shelf_no) {
            this.shelf_no = shelf_no;
        }
    }
}
