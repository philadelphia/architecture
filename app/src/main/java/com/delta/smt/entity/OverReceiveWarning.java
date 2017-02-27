package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveWarning {
    /**
     * code : 0
     * msg : Success
     * rows : [{"id":57702,"line_name":"T07","slot":"01T006","material_no":"4020108300","amount":3000,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"A1D001"},{"id":57702,"line_name":"T07","slot":"01T011","material_no":"0341299332","amount":2000,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"D1NC3"},{"id":57702,"line_name":"T07","slot":"04T012","material_no":"210532000105","amount":1500,"issue_amount":0,"status":4,"remain_time":900,"shelf_no":"D3IB2"}]
     */

    private String code;
    private String msg;
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
        /**
         * id : 57702
         * line_name : T07
         * slot : 01T006
         * material_no : 4020108300
         * amount : 3000
         * issue_amount : 0
         * status : 4
         * remain_time : 900
         * shelf_no : A1D001
         */

        private String id;
        private String line_name;
        private String slot;
        private String material_no;
        private String amount;
        private String issue_amount;
        private String status;
        private String remain_time;
        private String shelf_no;

        public RowsBean(String id, String line_name, String slot, String material_no, String amount, String issue_amount, String status, String shelf_no, String remain_time) {
            this.amount = amount;
            this.id = id;
            this.issue_amount = issue_amount;
            this.line_name = line_name;
            this.material_no = material_no;
            this.shelf_no = shelf_no;
            this.slot = slot;
            this.status = status;
            this.remain_time = remain_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getIssue_amount() {
            return issue_amount;
        }

        public void setIssue_amount(String issue_amount) {
            this.issue_amount = issue_amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemain_time() {
            return remain_time;
        }

        public void setRemain_time(String remain_time) {
            this.remain_time = remain_time;
        }

        public String getShelf_no() {
            return shelf_no;
        }

        public void setShelf_no(String shelf_no) {
            this.shelf_no = shelf_no;
        }
    }

    /**
     * code : 0
     * msg : Success
     * rows : {"isSuccess":true,"message":"unknow","data":[{"line":"H-01","material_num":"4020108700","shevles":"A1D001","re_quantity":"1470","rest_time":"1","status":"等待超领发料","issure_quantity":0},{"line":"H-01","material_num":"3460016900","shevles":"A1E005","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0},{"line":"H-01","material_num":"4020108400","shevles":"A1D001","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0}]}
     *//*

    private String code;
    private String msg;
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
        *//**
     * isSuccess : true
     * message : unknow
     * data : [{"line":"H-01","material_num":"4020108700","shevles":"A1D001","re_quantity":"1470","rest_time":"1","status":"等待超领发料","issure_quantity":0},{"line":"H-01","material_num":"3460016900","shevles":"A1E005","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0},{"line":"H-01","material_num":"4020108400","shevles":"A1D001","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0}]
     *//*



            private String id;
            private String line_name;
            private String slot;
            private String material_no;
            private String amount;
            private String issue_amount;
            private String status;
            private String shelf_no;
            private String remain_time;

            public RowsBean(String id, String line_name,String slot,String material_no,String amount, String issue_amount,  String status,   String shelf_no,String remain_time) {
                this.amount = amount;
                this.id = id;
                this.issue_amount = issue_amount;
                this.line_name = line_name;
                this.material_no = material_no;
                this.shelf_no = shelf_no;
                this.slot = slot;
                this.status = status;
                this.remain_time = remain_time;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIssue_amount() {
                return issue_amount;
            }

            public void setIssue_amount(String issue_amount) {
                this.issue_amount = issue_amount;
            }

            public String getLine_name() {
                return line_name;
            }

            public void setLine_name(String line_name) {
                this.line_name = line_name;
            }

            public String getMaterial_no() {
                return material_no;
            }

            public void setMaterial_no(String material_no) {
                this.material_no = material_no;
            }

            public String getShelf_no() {
                return shelf_no;
            }

            public void setShelf_no(String shelf_no) {
                this.shelf_no = shelf_no;
            }

            public String getSlot() {
                return slot;
            }

            public void setSlot(String slot) {
                this.slot = slot;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemain_time() {
                return remain_time;
            }

            public void setRemain_time(String remain_time) {
                this.remain_time = remain_time;
            }
        }*/



}
