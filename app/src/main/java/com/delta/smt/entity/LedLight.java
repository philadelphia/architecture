package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-03-14.
 */

public class LedLight {

    /**
     * code : 0
     * msg : Success
     * rows : [{"id":6,"shelfSerial":"A","subshelfSerial":"A1AA2","lightSerial":"","code":"A1AA2","height":2,"lightStatus":1},{"id":9,"shelfSerial":"A","subshelfSerial":"A1AD2","lightSerial":"","code":"A1AD2","height":2,"lightStatus":1},{"id":7,"shelfSerial":"A","subshelfSerial":"A1AB2","lightSerial":"","code":"A1AB2","height":2,"lightStatus":1},{"id":10,"shelfSerial":"A","subshelfSerial":"A1AE2","lightSerial":"","code":"A1AE2","height":2,"lightStatus":1},{"id":8,"shelfSerial":"A","subshelfSerial":"A1AC2","lightSerial":"","code":"A1AC2","height":2,"lightStatus":1},{"id":1,"shelfSerial":"A","subshelfSerial":"A1AA1","lightSerial":"A1AA1","code":"A1AA1","height":1,"lightStatus":1},{"id":2,"shelfSerial":"A","subshelfSerial":"A1AB1","lightSerial":"A1AB1","code":"A1AB1","height":1,"lightStatus":1},{"id":3,"shelfSerial":"A","subshelfSerial":"A1AC1","lightSerial":"A1AC1","code":"A1AC1","height":1,"lightStatus":1},{"id":4,"shelfSerial":"A","subshelfSerial":"A1AD1","lightSerial":"A1AD1","code":"A1AD1","height":1,"lightStatus":1},{"id":5,"shelfSerial":"A","subshelfSerial":"A1AE1","lightSerial":"A1AE1","code":"A1AE1","height":1,"lightStatus":1}]
     */

    private String code;
    private String msg;
    /**
     * id : 6
     * shelfSerial : A
     * subshelfSerial : A1AA2
     * lightSerial :
     * code : A1AA2
     * height : 2
     * lightStatus : 1
     */

    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int id;
        private String shelfSerial;
        private String subshelfSerial;
        private String lightSerial;
        private String code;
        private int height;
        private int lightStatus;

        private int color=4;
        private String bid;
        private String  framelocation;

        public RowsBean(String shelfSerial, String bid, String framelocation) {
            this.shelfSerial = shelfSerial;
            this.bid = bid;
            this.framelocation = framelocation;
        }

        public String getFramelocation() {
            return framelocation;
        }

        public void setFramelocation(String framelocation) {
            this.framelocation = framelocation;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShelfSerial() {
            return shelfSerial;
        }

        public void setShelfSerial(String shelfSerial) {
            this.shelfSerial = shelfSerial;
        }

        public String getSubshelfSerial() {
            return subshelfSerial;
        }

        public void setSubshelfSerial(String subshelfSerial) {
            this.subshelfSerial = subshelfSerial;
        }

        public String getLightSerial() {
            return lightSerial;
        }

        public void setLightSerial(String lightSerial) {
            this.lightSerial = lightSerial;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLightStatus() {
            return lightStatus;
        }

        public void setLightStatus(int lightStatus) {
            this.lightStatus = lightStatus;
        }
    }
}
