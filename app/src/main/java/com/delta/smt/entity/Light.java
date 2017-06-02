package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-01-12.
 */

public class Light {

    /**
     * code : 0
     * msg : Success
     * rows : [{"shelfSerial":"A","subShelfSerial":"A-1-1","height":1,"lightSerial":"led-01","lightStatus":1}]
     */

    private String code;
    private String message;
    /**
     * shelfSerial : A
     * subShelfSerial : A-1-1
     * height : 1
     * lightSerial : led-01
     * lightStatus : 1
     */

    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String shelfSerial;
        private String subShelfSerial;
        private int height;
        private String lightSerial;
        private int lightStatus;

        public String getShelfSerial() {
            return shelfSerial;
        }

        public void setShelfSerial(String shelfSerial) {
            this.shelfSerial = shelfSerial;
        }

        public String getSubShelfSerial() {
            return subShelfSerial;
        }

        public void setSubShelfSerial(String subShelfSerial) {
            this.subShelfSerial = subShelfSerial;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getLightSerial() {
            return lightSerial;
        }

        public void setLightSerial(String lightSerial) {
            this.lightSerial = lightSerial;
        }

        public int getLightStatus() {
            return lightStatus;
        }

        public void setLightStatus(int lightStatus) {
            this.lightStatus = lightStatus;
        }
    }
}
