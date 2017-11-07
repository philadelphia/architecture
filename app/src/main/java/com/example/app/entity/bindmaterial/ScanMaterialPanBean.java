package com.example.app.entity.bindmaterial;

import java.util.List;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 16:20
 * @description :
 */

public class ScanMaterialPanBean {


    /**
     * code : 0
     * message : Success
     * rows : {"carName":"IP-CAR-A01","storageNum":"170712140724","storageBind":[{"materialNo":"0313300001","materialTotal":4,"moveLabel":"T-102","shelf":"D1BE5"},{"materialNo":"0341041301","materialTotal":1,"moveLabel":"T-103","shelf":"D1DC4/1"},{"materialNo":"0341041301","materialTotal":2,"moveLabel":"N/A","shelf":"D1DC4/1"},{"materialNo":"0341164332","materialTotal":4,"moveLabel":"T-108","shelf":"D1GC4/7"}]}
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
         * storageNum : 170712140724
         * storageBind : [{"materialNo":"0313300001","materialTotal":4,"moveLabel":"T-102","shelf":"D1BE5"},{"materialNo":"0341041301","materialTotal":1,"moveLabel":"T-103","shelf":"D1DC4/1"},{"materialNo":"0341041301","materialTotal":2,"moveLabel":"N/A","shelf":"D1DC4/1"},{"materialNo":"0341164332","materialTotal":4,"moveLabel":"T-108","shelf":"D1GC4/7"}]
         */

        private String carName;
        private String storageNum;
        private List<StorageBindBean> storageBind;

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

        public List<StorageBindBean> getStorageBind() {
            return storageBind;
        }

        public void setStorageBind(List<StorageBindBean> storageBind) {
            this.storageBind = storageBind;
        }

     /*   public static class StorageBindBean {
            *//**
             * materialNo : 0313300001
             * materialTotal : 4
             * moveLabel : T-102
             * shelf : D1BE5
             *//*

            private String materialNo;
            private int materialTotal;
            private String moveLabel;
            private String shelf;

            public String getMaterialNo() {
                return materialNo;
            }

            public void setMaterialNo(String materialNo) {
                this.materialNo = materialNo;
            }

            public int getMaterialTotal() {
                return materialTotal;
            }

            public void setMaterialTotal(int materialTotal) {
                this.materialTotal = materialTotal;
            }

            public String getMoveLabel() {
                return moveLabel;
            }

            public void setMoveLabel(String moveLabel) {
                this.moveLabel = moveLabel;
            }

            public String getShelf() {
                return shelf;
            }

            public void setShelf(String shelf) {
                this.shelf = shelf;
            }
        }*/
    }
}
