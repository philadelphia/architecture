package com.delta.smt.entity;

import java.io.Serializable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public class Product_mToolsInfo implements Serializable{

    private String TurnNumber;
    private String ProductToolsBarCode;
    private String ProductToolsType;
    private String ProductToolsLocation;

    public Product_mToolsInfo(String turnNumber, String productToolsBarCode, String productToolsType, String productToolsLocation) {
        TurnNumber = turnNumber;
        ProductToolsBarCode = productToolsBarCode;
        ProductToolsType = productToolsType;
        ProductToolsLocation = productToolsLocation;
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

    public String getProductToolsType() {
        return ProductToolsType;
    }

    public void setProductToolsType(String productToolsType) {
        ProductToolsType = productToolsType;
    }

    public String getProductToolsLocation() {
        return ProductToolsLocation;
    }

    public void setProductToolsLocation(String productToolsLocation) {
        ProductToolsLocation = productToolsLocation;
    }
}
