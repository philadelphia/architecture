package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2017-01-15.
 */

public class PcbNumber {

    /**
     * code : 0
     * msg : success
     * data : {"id":1,"amount":50}
     */

    private String code;
    private String message;
    /**
     * id : 1
     * amount : 50
     */

    private DataBean rows;

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

    public DataBean getRows() {
        return rows;
    }

    public void setRows(DataBean rows) {
        this.rows = rows;
    }

    public static class DataBean {
        private int id;
        private int amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
