package com.delta.smt.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */
public class MantissaWarehousePutstorageBindTagResult {

    /**
     * msg : success
     * rows : [{"label":"11111","material_num":"4020108700","serial_num":"12344","shelves":"A1D001","status":1}]
     */

    private String code;
    @SerializedName("message")
    private String msg;
    private MantissaWarehousePutstorageBindTag rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MantissaWarehousePutstorageBindTag getrows() {
        return rows;
    }

    public void setrows(MantissaWarehousePutstorageBindTag rows) {
        this.rows = rows;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static class MantissaWarehousePutstorageBindTag {
        /**
         * label : 11111
         * material_num : 4020108700
         * serial_num : 12344
         * shelves : A1D001
         * status : 1
         */

        //入库编号
        private String storageNum;

        //料车名
        private String carName;

        //绑定列表
        private List<storageBindList> storageBind;

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getStorageNum() {
            return storageNum;
        }

        public void setStorageNum(String storageNum) {
            this.storageNum = storageNum;
        }

        public List<storageBindList> getStorageBind() {
            return storageBind;
        }

        public void setStorageBind(List<storageBindList> storageBind) {
            this.storageBind = storageBind;
        }


        public static class storageBindList {

            //料盘料号
            private String materialNo;
            //料盘数量
            private String materialTotal;
            //移动标签
            private String moveLabel;
            //架位
            private String shelf;


            public storageBindList(String materialNo, String materialTotal, String moveLabel, String shelf) {
                this.materialNo = materialNo;
                this.materialTotal = materialTotal;
                this.moveLabel = moveLabel;
                this.shelf = shelf;
            }

            public String getMaterialNo() {
                return materialNo;
            }

            public void setMaterialNo(String materialNo) {
                this.materialNo = materialNo;
            }

            public String getMaterialTotal() {
                return materialTotal;
            }

            public void setMaterialTotal(String materialTotal) {
                this.materialTotal = materialTotal;
            }

            public String getMoveLabel() {
                return moveLabel;
            }

            public void setMoveLabel(String moveLabel) {
                this.moveLabel = moveLabel;
            }

            public String getShelf() {
                return shelf;
            }

            public void setShelf(String shelf) {
                this.shelf = shelf;
            }
        }



    }
}
