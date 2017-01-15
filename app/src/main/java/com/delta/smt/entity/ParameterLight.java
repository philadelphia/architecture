package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-01-13.
 */

public class ParameterLight {

    /**
     * serial : 12345
     * partNum : abcdef
     * pcbCode : 1234
     * dateCode : 2017003
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String serial;
        private String partNum;
        private String pcbCode;
        private String dateCode;
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        private String subShelfCode;

        public String getSubShelfCode() {
            return subShelfCode;
        }

        public void setSubShelfCode(String subShelfCode) {
            this.subShelfCode = subShelfCode;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
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
