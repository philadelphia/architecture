package com.example.app.entity;

import java.io.Serializable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProductToolsInfo implements Serializable{

    String turnNumber;
    String productToolsBarCode;
    String produceToolsType;
    String productToolsLocation;
    String reSelect;
    String status;
    String jigTypeId;
    String jigId;

    public ProductToolsInfo(String turnNumber, String productToolsBarCode, String produceToolsType, String productToolsLocation, String reSelect, String status, String jigTypeId, String jigID) {
        this.turnNumber = turnNumber;
        this.productToolsBarCode = productToolsBarCode;
        this.produceToolsType = produceToolsType;
        this.productToolsLocation = productToolsLocation;
        this.reSelect = reSelect;
        this.status = status;
        this.jigTypeId = jigTypeId;
        this.jigId = jigID;
    }

    public String getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(String turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getProductToolsBarCode() {
        return productToolsBarCode;
    }

    public void setProductToolsBarCode(String productToolsBarCode) {
        this.productToolsBarCode = productToolsBarCode;
    }

    public String getProduceToolsType() {
        return produceToolsType;
    }

    public void setProduceToolsType(String produceToolsType) {
        this.produceToolsType = produceToolsType;
    }

    public String getProductToolsLocation() {
        return productToolsLocation;
    }

    public void setProductToolsLocation(String productToolsLocation) {
        this.productToolsLocation = productToolsLocation;
    }

    public String getReSelect() {
        return reSelect;
    }

    public void setReSelect(String reSelect) {
        this.reSelect = reSelect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJigTypeId() {
        return jigTypeId;
    }

    public void setJigTypeId(String jigTypeId) {
        this.jigTypeId = jigTypeId;
    }

    public String getJigId() {
        return jigId;
    }

    public void setJigId(String jigId) {
        this.jigId = jigId;
    }
}
