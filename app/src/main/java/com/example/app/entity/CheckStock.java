package com.example.app.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public class CheckStock {

    /**
     * code : 0
     * msg : success
     * rows : [{"id":1,"partNum":"0343362301","subShelfSerial":"A11-1","boxSerial":"1234342","pcbCode":"01","dateCode":"1620","boundCount":600,"realCount":0,"date":"2016/12/13","userName":"admin","status":"未开始","description":""},{"id":1,"partNum":"0343362301","subShelfSerial":"A11-1","boxSerial":"23423424","pcbCode":"01","dateCode":"1620","boundCount":600,"realCount":0,"date":"2016/12/13","userName":"admin","status":"未开始","description":""}]
     */

    private String code;
    private String message;
    /**
     * id : 1
     * partNum : 0343362301
     * subShelfSerial : A11-1
     * boxSerial : 1234342
     * pcbCode : 01
     * dateCode : 1620
     * boundCount : 600
     * realCount : 0
     * date : 2016/12/13
     * userName : admin
     * status : 未开始
     * description :
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
        private int id;
        private String partNum;
        private String labelCode;
        private String boxSerial;
        private String pcbCode;
        private String dateCode;
        private int boundCount;
        private int realCount;
        private String date;
        private String userName;
        private String status;
        private String description;
        private boolean isColor;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isColor() {
            return isColor;
        }

        public void setColor(boolean color) {
            isColor = color;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }


        public String getLabelCode() {
            return labelCode;
        }

        public void setLabelCode(String labelCode) {
            this.labelCode = labelCode;
        }

        public String getBoxSerial() {
            return boxSerial;
        }

        public void setBoxSerial(String boxSerial) {
            this.boxSerial = boxSerial;
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

        public int getBoundCount() {
            return boundCount;
        }

        public void setBoundCount(int boundCount) {
            this.boundCount = boundCount;
        }

        public int getRealCount() {
            return realCount;
        }

        public void setRealCount(int realCount) {
            this.realCount = realCount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
