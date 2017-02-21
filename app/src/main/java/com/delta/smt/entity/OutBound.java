package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-01-15.
 */

public class OutBound {

    /**
     * code : 0
     * msg : success
     * data : [{"partNum":"0343352301","subShelfSerial":"J21-34","pcbCode":"01","dateCode":"20045"},{"partNum":"0343352301","subShelfSerial":"J21-35","pcbCode":"02","dateCode":"20046"}]
     */

    private String code;
    private String msg;
    /**
     * partNum : 0343352301
     * subShelfSerial : J21-34
     * pcbCode : 01
     * dateCode : 20045
     */

    private List<DataBean> rows;

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

    public List<DataBean> getRows() {
        return rows;
    }

    public void setRows(List<DataBean> rows) {
        this.rows = rows;
    }

    public static class DataBean {
        private String partNum;
        private String subShelfSerial;
        private String pcbCode;
        private String dateCode;
        private String boxSerial;
        private int count;
        private int  id;

        public String getBoxSerial() {
            return boxSerial;
        }

        public void setBoxSerial(String boxSerial) {
            this.boxSerial = boxSerial;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int isColor=0;

        public int getIsColor() {
            return isColor;
        }

        public void setIsColor(int isColor) {
            this.isColor = isColor;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public String getSubShelfSerial() {
            return subShelfSerial;
        }

        public void setSubShelfSerial(String subShelfSerial) {
            this.subShelfSerial = subShelfSerial;
        }

        public String getPcbCode() {
            return pcbCode;
        }

        public void setPcbCode(String pcbCode) {
            this.pcbCode = pcbCode;
        }

        public String getDateCode() {
            return dateCode;
        }

        public void setDateCode(String dateCode) {
            this.dateCode = dateCode;
        }
    }
}
