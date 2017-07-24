package com.delta.smt.entity.bindmaterial;

import java.util.List;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 15:40
 * @description :绑定入库料车
 */

public class BindCarBean {


    /**
     * code : 0
     * message : Success
     * rows : {"carName":"IP-CAR-A01","storageNum":"170705172345","storageBind":[]}
     */

    private int code;
    private String message;
    private RowsBean rows;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * carName : IP-CAR-A01
         * storageNum : 170705172345
         * storageBind : []
         */

        private String carName;
        private String storageNum;
        private List<?> storageBind;

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getStorageNum() {
            return storageNum;
        }

        public void setStorageNum(String storageNum) {
            this.storageNum = storageNum;
        }

        public List<?> getStorageBind() {
            return storageBind;
        }

        public void setStorageBind(List<?> storageBind) {
            this.storageBind = storageBind;
        }
    }
}
