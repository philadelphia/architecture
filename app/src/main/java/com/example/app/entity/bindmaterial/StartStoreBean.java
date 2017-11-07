package com.example.app.entity.bindmaterial;

import java.util.List;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 13:50
 * @description :开始新物料入库
 */

public class StartStoreBean {

    /**
     * code : 0
     * message : Success
     * rows : {"storageNum":"170705172345","storageBind":[]}
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
         * storageNum : 170705172345
         * storageBind : []
         */

        private String storageNum;
        private List<?> storageBind;

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
