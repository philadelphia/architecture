package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-02-20.
 */

public class OnGoing {

    /**
     * code : 0
     * msg : Success
     * rows : {"id":1,"startTime":"Apr 13, 2017 9:36:22 AM","status":"盘点中","userName":"admin","completedSubShelf":[{"subshelf":"A1AA1","shelf":"A","floor":"A1","height":"1","status":2},{"subshelf":"A1AB1","shelf":"A","floor":"A1","height":"1","status":1},{"subshelf":"A1AC1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AD1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AE1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AA2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AB2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AC2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AD2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AE2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1BA1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BB1","shelf":"B","floor":"A1","height":"1","status":0},{"subshelf":"A1BC1","shelf":"B","floor":"A1","height":"1","status":0},{"subshelf":"A1BD1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BE1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BA2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BB2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BC2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BD2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BE2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1CA1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CB1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CC1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CD1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CE1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CA2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CB2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CC2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CD2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CE2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1FA1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FB1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FC1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FD1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FE1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FF1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FG1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FH1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FI1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FJ1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"Floor_Test","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FA2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FB2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FC2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FD2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FE2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FF2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FG2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FH2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FI2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FJ2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"Floor_test_2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FA3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FB3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FC3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FD3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FE3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FF3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FG3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FH3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FI3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FJ3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1KA1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KB1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KC1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KD1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KE1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KF1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KA2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KB2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KC2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KD2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KE2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KF2","shelf":"K","floor":"A1","height":"2","status":3}]}
     */

    private String code;
    private String msg;
    /**
     * id : 1
     * startTime : Apr 13, 2017 9:36:22 AM
     * status : 盘点中
     * userName : admin
     * completedSubShelf : [{"subshelf":"A1AA1","shelf":"A","floor":"A1","height":"1","status":2},{"subshelf":"A1AB1","shelf":"A","floor":"A1","height":"1","status":1},{"subshelf":"A1AC1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AD1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AE1","shelf":"A","floor":"A1","height":"1","status":0},{"subshelf":"A1AA2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AB2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AC2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AD2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1AE2","shelf":"A","floor":"A1","height":"2","status":3},{"subshelf":"A1BA1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BB1","shelf":"B","floor":"A1","height":"1","status":0},{"subshelf":"A1BC1","shelf":"B","floor":"A1","height":"1","status":0},{"subshelf":"A1BD1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BE1","shelf":"B","floor":"A1","height":"1","status":3},{"subshelf":"A1BA2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BB2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BC2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BD2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1BE2","shelf":"B","floor":"A1","height":"2","status":3},{"subshelf":"A1CA1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CB1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CC1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CD1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CE1","shelf":"C","floor":"A1","height":"1","status":3},{"subshelf":"A1CA2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CB2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CC2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CD2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1CE2","shelf":"C","floor":"A1","height":"2","status":3},{"subshelf":"A1FA1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FB1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FC1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FD1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FE1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FF1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FG1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FH1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FI1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FJ1","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"Floor_Test","shelf":"F","floor":"A1","height":"1","status":3},{"subshelf":"A1FA2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FB2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FC2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FD2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FE2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FF2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FG2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FH2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FI2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FJ2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"Floor_test_2","shelf":"F","floor":"A1","height":"2","status":3},{"subshelf":"A1FA3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FB3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FC3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FD3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FE3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FF3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FG3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FH3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FI3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1FJ3","shelf":"F","floor":"A1","height":"3","status":3},{"subshelf":"A1KA1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KB1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KC1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KD1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KE1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KF1","shelf":"K","floor":"A1","height":"1","status":3},{"subshelf":"A1KA2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KB2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KC2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KD2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KE2","shelf":"K","floor":"A1","height":"2","status":3},{"subshelf":"A1KF2","shelf":"K","floor":"A1","height":"2","status":3}]
     */

    private RowsBean rows;

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

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int id;
        private String startTime;
        private String status;
        private String userName;
        /**
         * subshelf : A1AA1
         * shelf : A
         * floor : A1
         * height : 1
         * status : 2
         */

        private List<CompletedSubShelfBean> completedSubShelf;

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

        public List<CompletedSubShelfBean> getCompletedSubShelf() {
            return completedSubShelf;
        }

        public void setCompletedSubShelf(List<CompletedSubShelfBean> completedSubShelf) {
            this.completedSubShelf = completedSubShelf;
        }

        public static class CompletedSubShelfBean {
            private String subshelf;
            private String shelf;
            private String floor;
            private String height;
            private int status;

            public String getSubshelf() {
                return subshelf;
            }

            public void setSubshelf(String subshelf) {
                this.subshelf = subshelf;
            }

            public String getShelf() {
                return shelf;
            }

            public void setShelf(String shelf) {
                this.shelf = shelf;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
