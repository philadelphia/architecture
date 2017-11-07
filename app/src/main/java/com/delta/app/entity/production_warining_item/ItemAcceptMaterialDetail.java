package com.delta.app.entity.production_warining_item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class ItemAcceptMaterialDetail {

    /**
     * code : 0
     * msg : Success
     * rows : {"line":"T13","connectMaterialCount":2,"lineMaterialEntities":[{"line":"T13","partNumber":"PN001","slot":"050","quantity":0,"serialNumber":"SN001","unit":"PCS","location":"RP1301","status":1,"id":1},{"line":"T13","partNumber":"PN002","slot":"060","quantity":200,"serialNumber":"SN002","unit":"PCS","location":"RP1305","status":1,"id":2}]}
     */

    private String code;
    @SerializedName("message")
    private String msg;
    private RowsBean rows;

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

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * line : T13
         * connectMaterialCount : 2
         * lineMaterialEntities : [{"line":"T13","partNumber":"PN001","slot":"050","quantity":0,"serialNumber":"SN001","unit":"PCS","location":"RP1301","status":1,"id":1},{"line":"T13","partNumber":"PN002","slot":"060","quantity":200,"serialNumber":"SN002","unit":"PCS","location":"RP1305","status":1,"id":2}]
         */

        private String line;
        private int connectMaterialCount;
        private List<LineMaterialEntitiesBean> lineMaterialEntities;

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public int getConnectMaterialCount() {
            return connectMaterialCount;
        }

        public void setConnectMaterialCount(int connectMaterialCount) {
            this.connectMaterialCount = connectMaterialCount;
        }

        public List<LineMaterialEntitiesBean> getLineMaterialEntities() {
            return lineMaterialEntities;
        }

        public void setLineMaterialEntities(List<LineMaterialEntitiesBean> lineMaterialEntities) {
            this.lineMaterialEntities = lineMaterialEntities;
        }

        public static class LineMaterialEntitiesBean {
            /**
             * line : T13
             * partNumber : PN001
             * slot : 050
             * quantity : 0
             * serialNumber : SN001
             * unit : PCS
             * location : RP1301
             * status : 1
             * id : 1
             */

            private String line;
            @SerializedName("material_no")
            private String partNumber;
            private String slot;
            @SerializedName("qty")
            private int quantity;
            @SerializedName("serial_no")
            private String serialNumber;
            private String unit;
            private String location;
            private int status;
            private int id;

            private int mode;

            public int getMode() {
                return mode;
            }

            public void setMode(int mode) {
                this.mode = mode;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getPartNumber() {
                return partNumber;
            }

            public void setPartNumber(String partNumber) {
                this.partNumber = partNumber;
            }

            public String getSlot() {
                return slot;
            }

            public void setSlot(String slot) {
                this.slot = slot;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getSerialNumber() {
                return serialNumber;
            }

            public void setSerialNumber(String serialNumber) {
                this.serialNumber = serialNumber;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
