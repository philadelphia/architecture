package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-02-20.
 */

public class OnGoing {
    /**
     * code : 0
     * msg : success
     * rows : [{"id":2,"startTime":"2016/12/13","endTime":"","status":"盘点中","userName":"admin","completedSubShelf":["\u201cA1-2\u201d","\u201dA1-3\u201d"]}]
     */

    private String code;
    private String msg;
    /**
     * id : 2
     * startTime : 2016/12/13
     * endTime :
     * status : 盘点中
     * userName : admin
     * completedSubShelf : ["\u201cA1-2\u201d","\u201dA1-3\u201d"]
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
        private String startTime;
        private String endTime;
        private String status;
        private String userName;
        private List<String> completedSubShelf;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<String> getCompletedSubShelf() {
            return completedSubShelf;
        }

        public void setCompletedSubShelf(List<String> completedSubShelf) {
            this.completedSubShelf = completedSubShelf;
        }
    }
}
