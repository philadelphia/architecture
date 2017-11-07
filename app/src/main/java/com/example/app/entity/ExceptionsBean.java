package com.example.app.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-01-22.
 */

public class ExceptionsBean {

    /**
     * msg : Success
     * rows : [{"status":5,"boundCount":2000,"partNum":"4020108700","boxSerial":"0312344"},{"status":0,"boundCount":2000,"partNum":"4020112600","boxSerial":"0412346"}]
     * code : 0
     */

    private String message;
    private String code;
    /**
     * status : 5
     * boundCount : 2000
     * partNum : 4020108700
     * boxSerial : 0312344
     */

    private List<RowsBean> rows;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int status;
        private int boundCount;
        private String partNum;
        private String boxSerial;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBoundCount() {
            return boundCount;
        }

        public void setBoundCount(int boundCount) {
            this.boundCount = boundCount;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public String getBoxSerial() {
            return boxSerial;
        }

        public void setBoxSerial(String boxSerial) {
            this.boxSerial = boxSerial;
        }
    }
}
