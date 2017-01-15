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

    private int code;
    private String msg;
    /**
     * id : 1
     * amount : 50
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
