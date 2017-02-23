package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-02-20.
 */

public class OnGoing {


    /**
     * msg : Success
     * rows : {"startTime":"Feb 22, 2017 2:03:08 PM","id":6,"userName":"admin","completedSubShelf":["A-1-1"],"status":"盘点中"}
     * code : 0
     */

    private String msg;
    /**
     * startTime : Feb 22, 2017 2:03:08 PM
     * id : 6
     * userName : admin
     * completedSubShelf : ["A-1-1"]
     * status : 盘点中
     */

    private RowsBean rows;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class RowsBean {
        private String startTime;
        private int id;
        private String userName;
        private String status;
        private List<String> completedSubShelf;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public List<String> getCompletedSubShelf() {
            return completedSubShelf;
        }

        public void setCompletedSubShelf(List<String> completedSubShelf) {
            this.completedSubShelf = completedSubShelf;
        }
    }
}
