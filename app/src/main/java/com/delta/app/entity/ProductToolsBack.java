package com.delta.app.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public class ProductToolsBack {

    String TurnNumber;
    String ProductToolsBarCode;
    String WorkNumber;
    String ProductToolsType;
    String Status;

    public ProductToolsBack(String turnNumber, String productToolsBarCode, String workNumber, String productToolsType, String status) {
        TurnNumber = turnNumber;
        ProductToolsBarCode = productToolsBarCode;
        WorkNumber = workNumber;
        ProductToolsType = productToolsType;
        Status = status;
    }

    public String getTurnNumber() {
        return TurnNumber;
    }

    public void setTurnNumber(String turnNumber) {
        TurnNumber = turnNumber;
    }

    public String getProductToolsBarCode() {
        return ProductToolsBarCode;
    }

    public void setProductToolsBarCode(String productToolsBarCode) {
        ProductToolsBarCode = productToolsBarCode;
    }

    public String getWorkNumber() {
        return WorkNumber;
    }

    public void setWorkNumber(String workNumber) {
        WorkNumber = workNumber;
    }

    public String getProductToolsType() {
        return ProductToolsType;
    }

    public void setProductToolsType(String productToolsType) {
        ProductToolsType = productToolsType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
