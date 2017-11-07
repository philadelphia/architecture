package com.example.app.entity;

/**
 * Created by Shufeng.Wu on 2017/1/19.
 */

public class ModuleUpDownWarningBean {

    /**
     * message : {"deadLine":"1484805419093","productline":"h13"}
     * type : 11
     */

    private MessageBean message;
    private int type;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class MessageBean {
        /**
         * deadLine : 1484805419093
         * productline : h13
         */

        private String deadLine;
        private String productline;

        public String getDeadLine() {
            return deadLine;
        }

        public void setDeadLine(String deadLine) {
            this.deadLine = deadLine;
        }

        public String getProductline() {
            return productline;
        }

        public void setProductline(String productline) {
            this.productline = productline;
        }
    }
}
