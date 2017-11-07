package com.example.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/16 17:48
 */


public class WarningContent {
    /**
     * type : 0
     * message : {"productline":"H-13","deadLine":"2342424"}
     */
    private int type;
    /**
     * productline : H-13
     * deadLine : 2342424
     */

    private MessageBean message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        private String productline;
        private String deadLine;

        public String getProductline() {
            return productline;
        }

        public void setProductline(String productline) {
            this.productline = productline;
        }

        public String getDeadLine() {
            return deadLine;
        }

        public void setDeadLine(String deadLine) {
            this.deadLine = deadLine;
        }
    }
}
