package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/18 10:55
 */


public class FaultSolutionMessage {

    /**
     * code : 0
     * msg : Success
     * rows : [{"faultSolutionId":1,"content":"启动AOI检测软件","orderNum":1,"creater":"admin","createTime":"Jan 17, 2017 3:04:58 PM","id":1},{"faultSolutionId":1,"content":"选择当前操作的程式","orderNum":2,"creater":"admin","createTime":"Jan 17, 2017 3:05:39 PM","id":2},{"faultSolutionId":1,"content":"点击运行按钮","orderNum":3,"creater":"admin","id":3}]
     */

    private String code;
    private String msg;
    /**
     * faultSolutionId : 1
     * content : 启动AOI检测软件
     * orderNum : 1
     * creater : admin
     * createTime : Jan 17, 2017 3:04:58 PM
     * id : 1
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
        private int faultSolutionId;
        private String content;
        private int orderNum;
        private String creater;
        private String createTime;
        private int id;

        public int getFaultSolutionId() {
            return faultSolutionId;
        }

        public void setFaultSolutionId(int faultSolutionId) {
            this.faultSolutionId = faultSolutionId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
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
