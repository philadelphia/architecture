package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveWarning {

    /**
     * code : 0
     * msg : Success
     * rows : {"isSuccess":true,"message":"unknow","data":[{"line":"H-01","material_num":"4020108700","shevles":"A1D001","re_quantity":"1470","rest_time":"1","status":"等待超领发料","issure_quantity":0},{"line":"H-01","material_num":"3460016900","shevles":"A1E005","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0},{"line":"H-01","material_num":"4020108400","shevles":"A1D001","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0}]}
     */

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
        /**
         * isSuccess : true
         * message : unknow
         * data : [{"line":"H-01","material_num":"4020108700","shevles":"A1D001","re_quantity":"1470","rest_time":"1","status":"等待超领发料","issure_quantity":0},{"line":"H-01","material_num":"3460016900","shevles":"A1E005","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0},{"line":"H-01","material_num":"4020108400","shevles":"A1D001","re_quantity":"1490","rest_time":"0","status":"","issure_quantity":0}]
         */

        private boolean isSuccess;
        private String message;
        private List<DataBean> data;

        public boolean isIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * line : H-01
             * material_num : 4020108700
             * shevles : A1D001
             * re_quantity : 1470
             * rest_time : 1
             * status : 等待超领发料
             * issure_quantity : 0
             */

            private String line;
            private String material_num;
            private String shevles;
            private String re_quantity;
            private String rest_time;
            private String status;
            private int issure_quantity;

            public DataBean(String line, String material_num,String shevles, String re_quantity,String rest_time,  String status) {
                this.line = line;
                this.material_num = material_num;
                this.re_quantity = re_quantity;
                this.rest_time = rest_time;
                this.shevles = shevles;
                this.status = status;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getMaterial_num() {
                return material_num;
            }

            public void setMaterial_num(String material_num) {
                this.material_num = material_num;
            }

            public String getShevles() {
                return shevles;
            }

            public void setShevles(String shevles) {
                this.shevles = shevles;
            }

            public String getRe_quantity() {
                return re_quantity;
            }

            public void setRe_quantity(String re_quantity) {
                this.re_quantity = re_quantity;
            }

            public String getRest_time() {
                return rest_time;
            }

            public void setRest_time(String rest_time) {
                this.rest_time = rest_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getIssure_quantity() {
                return issure_quantity;
            }

            public void setIssure_quantity(int issure_quantity) {
                this.issure_quantity = issure_quantity;
            }
        }
    }
}
