package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/17 20:48
 */


public class SolutionMessage {

    /**
     * code : 0
     * msg : Success
     * rows : [{"name":"AOI机台发生故障，请联系厂商","faultCode":"AOI-0001","frequency":0,"creater":"admin","createTime":"Jan 17, 2017 2:48:16 PM","id":2},{"name":"修改AOI检测参数","faultCode":"AOI-0001","faultDetails":"1:启动AOI检测软件","frequency":0,"creater":"admin","createTime":"Jan 17, 2017 2:47:07 PM","id":1}]
     */

    private String code;
    private String msg;
    /**
     * name : AOI机台发生故障，请联系厂商
     * faultCode : AOI-0001
     * frequency : 0
     * creater : admin
     * createTime : Jan 17, 2017 2:48:16 PM
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

    public static class RowsBean {
        private String name;
        private String faultCode;
        private int frequency;
        private String creater;
        private String createTime;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFaultCode() {
            return faultCode;
        }

        public void setFaultCode(String faultCode) {
            this.faultCode = faultCode;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
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
