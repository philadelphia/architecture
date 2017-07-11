package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Lin.Hou on 2017-05-26.
 */

public class MaterialBlockBarCodeList {
    List<MaterialBlockBarCodefo> list;

    public List<MaterialBlockBarCodefo> getList() {
        return list;
    }

    public void setList(List<MaterialBlockBarCodefo> list) {
        this.list = list;
    }


    public static class MaterialBlockBarCodefo{
        String serial;//流水号
        String partNum;//料号
        String pcbCode;//PC
        String dateCode;//DC
        String count;//料号
        String unit;//单位
        String purchaseOrder;//购买订单
        String vender;//生产厂商
        String tradingNum;//交易代码
        String invoiceNum;//发票编号


        String labelCode;

        public String getSubShelfCode() {
            return labelCode;
        }

        public void setSubShelfCode(String labelCode) {
            this.labelCode = labelCode;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getPartNum() {
            return partNum;
        }

        public void setPartNum(String partNum) {
            this.partNum = partNum;
        }

        public String getPcbCode() {
            return pcbCode;
        }

        public void setPcbCode(String pcbCode) {
            this.pcbCode = pcbCode;
        }

        public String getDateCode() {
            return dateCode;
        }

        public void setDateCode(String dateCode) {
            this.dateCode = dateCode;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPurchaseOrder() {
            return purchaseOrder;
        }

        public void setPurchaseOrder(String purchaseOrder) {
            this.purchaseOrder = purchaseOrder;
        }

        public String getVender() {
            return vender;
        }

        public void setVender(String vender) {
            this.vender = vender;
        }

        public String getTradingNum() {
            return tradingNum;
        }

        public void setTradingNum(String tradingNum) {
            this.tradingNum = tradingNum;
        }

        public String getInvoiceNum() {
            return invoiceNum;
        }

        public void setInvoiceNum(String invoiceNum) {
            this.invoiceNum = invoiceNum;
        }
    }
}
