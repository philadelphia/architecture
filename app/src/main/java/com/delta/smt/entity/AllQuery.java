package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-01-17.
 */

public class AllQuery {

    /**
     * code : 0
     * msg : Success
     * currentPage : 1
     * pageSize : 2
     * totalcount : 30
     * total : 15
     * rows : [{"id":1,"productLine":"H13","sapWorkOrderId":"2016980001","partNum":"758254960284","machineType":"SG550X-24P-R","amount":"50","endTime":"2017/01/13 08:00","status":"待备料"},{"id":2,"productLine":"H13","sapWorkOrderId":"2016980001","partNum":"758254960284","machineType":"SG550X-24P-R","amount":"50","endTime":"2017/01/13 08:00","status":"备料中"}]
     */

    private String code;
    private String msg;
    private int currentPage;
    private int pageSize;
    private int totalcount;
    private int total;
    /**
     * id : 1
     * productLine : H13
     * sapWorkOrderId : 2016980001
     * partNum : 758254960284
     * machineType : SG550X-24P-R
     * amount : 50
     * endTime : 2017/01/13 08:00
     * status : 待备料
     */

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int id;
        private String productLine;
        private String sapWorkOrderId;
        private String partNum;
        private String machineType;
        private String amount;
        private String endTime;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductLine() {
            return productLine;
        }

        public void setProductLine(String productLine) {
            this.productLine = productLine;
        }

        public String getSapWorkOrderId() {
            return sapWorkOrderId;
        }

        public void setSapWorkOrderId(String sapWorkOrderId) {
            this.sapWorkOrderId = sapWorkOrderId;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public String getMachineType() {
            return machineType;
        }

        public void setMachineType(String machineType) {
            this.machineType = machineType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
