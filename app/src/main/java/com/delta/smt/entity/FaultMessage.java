package com.delta.smt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/17 15:18
 */


public class FaultMessage {

    /**
     * code : 0
     * msg : Success
     * rows : [{"faultType":"回焊炉-炉温过低","line":"H13","process":"回焊炉","faultMessage":"炉温过低","faultCode":"H-00001","isOk":0,"createTime":"Jan 17, 2017 4:50:08 PM","id":2},{"faultType":"AOI-直通率过低","line":"H13","process":"AOI","faultMessage":"直通率过低","faultCode":"AOI-00001","isOk":0,"createTime":"Jan 17, 2017 4:51:37 PM","id":3}]
     */

    private String code;
    private String msg;
    /**
     * faultType : 回焊炉-炉温过低
     * line : H13
     * process : 回焊炉
     * faultMessage : 炉温过低
     * faultCode : H-00001
     * isOk : 0
     * createTime : Jan 17, 2017 4:50:08 PM
     * id : 2
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

    public static class RowsBean implements Serializable{
        private String faultType;
        private String line;
        private String process;
        private String faultMessage;
        private String faultCode;
        private int isOk;
        private String createTime;
        private int id;
        private Long durationTime;

        public Long getDurationTime() {
            return durationTime;
        }

        public String getFaultType() {
            return faultType;
        }

        public void setFaultType(String faultType) {
            this.faultType = faultType;
        }

        public String getLine() {
            return line;
        }

        public void setDurationTime(Long durationTime) {
            this.durationTime = durationTime;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getFaultMessage() {
            return faultMessage;
        }

        public void setFaultMessage(String faultMessage) {
            this.faultMessage = faultMessage;
        }

        public String getFaultCode() {
            return faultCode;
        }

        public void setFaultCode(String faultCode) {
            this.faultCode = faultCode;
        }

        public int getIsOk() {
            return isOk;
        }

        public void setIsOk(int isOk) {
            this.isOk = isOk;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
