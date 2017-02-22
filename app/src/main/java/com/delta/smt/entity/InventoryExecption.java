package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-02-22.
 */

public class InventoryExecption {

    /**
     * msg : Success
     * rows : [{"realCount":0,"boundCount":18,"partNum":"0341236301","status":3,"description":"少料"},{"realCount":0,"boundCount":4000,"partNum":"758254960284","status":3,"description":"少料"},{"realCount":0,"boundCount":700,"partNum":"0341236302","status":3,"description":"少料"},{"realCount":0,"boundCount":235,"partNum":"0745799325","status":3,"description":"少料"},{"realCount":0,"boundCount":232,"partNum":"0458993256","status":3,"description":"少料"},{"realCount":0,"boundCount":250,"partNum":"0356892077","status":3,"description":"少料"},{"realCount":0,"boundCount":223,"partNum":"0356892076","status":3,"description":"少料"},{"realCount":0,"boundCount":40,"partNum":"0356892072","status":3,"description":"少料"},{"realCount":0,"boundCount":256,"partNum":"0757993256","status":3,"description":"少料"},{"realCount":0,"boundCount":200,"partNum":"0457945825","status":3,"description":"少料"},{"realCount":0,"boundCount":6266,"partNum":"0356892075","status":3,"description":"少料"},{"realCount":0,"boundCount":188,"partNum":"0357993256","status":3,"description":"少料"},{"realCount":0,"boundCount":150,"partNum":"4020108700","status":3,"description":"少料"},{"realCount":0,"boundCount":711,"partNum":"0457993256","status":3,"description":"少料"},{"realCount":0,"boundCount":300,"partNum":"0453263256","status":3,"description":"少料"},{"realCount":0,"boundCount":20,"partNum":"0356892078","status":3,"description":"少料"},{"realCount":0,"boundCount":200,"partNum":"4020112600","status":3,"description":"少料"},{"realCount":0,"boundCount":126,"partNum":"0356893256","status":3,"description":"少料"},{"realCount":0,"boundCount":100,"partNum":"2013568970","status":3,"description":"少料"}]
     * code : 0
     */

    private String msg;
    private String code;
    /**
     * realCount : 0
     * boundCount : 18
     * partNum : 0341236301
     * status : 3
     * description : 少料
     */

    private List<RowsBean> rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int realCount;
        private int boundCount;
        private String partNum;
        private int status;
        private String description;

        public int getRealCount() {
            return realCount;
        }

        public void setRealCount(int realCount) {
            this.realCount = realCount;
        }

        public int getBoundCount() {
            return boundCount;
        }

        public void setBoundCount(int boundCount) {
            this.boundCount = boundCount;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
