package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */
public class MantissaWarehousePutstorageResult {

    /**
     * msg : success
     * rows : [{"label":"11111","material_num":"4020108700","serial_num":"12344","shelves":"A1D001","status":1}]
     */

    private String code;
    private String msg;
    private List<MantissaWarehousePutstorage> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MantissaWarehousePutstorage> getrows() {
        return rows;
    }

    public void setrows(List<MantissaWarehousePutstorage> rows) {
        this.rows = rows;
    }


    public static class MantissaWarehousePutstorage {
        /**
         * label : 11111
         * material_num : 4020108700
         * serial_num : 12344
         * shelves : A1D001
         * status : 1
         */

        //标签
        private String label_name;
        //料号
        private String material_no;
        //尾数仓架位
        private String shelf_no;
        //主仓库架位
        private String to_shelf_no;
        //数量
        private String num;


        public MantissaWarehousePutstorage(String label_name, String material_no, String shelf_no, String to_shelf_no, String num) {
            this.label_name = label_name;
            this.material_no = material_no;
            this.shelf_no = shelf_no;
            this.to_shelf_no = to_shelf_no;
            this.num = num;
        }

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getShelf_no() {
            return shelf_no;
        }

        public void setShelf_no(String shelf_no) {
            this.shelf_no = shelf_no;
        }

        public String getTo_shelf_no() {
            return to_shelf_no;
        }

        public void setTo_shelf_no(String to_shelf_no) {
            this.to_shelf_no = to_shelf_no;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
