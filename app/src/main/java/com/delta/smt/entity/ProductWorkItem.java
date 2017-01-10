package com.delta.smt.entity;

import lombok.Data;

/**
 * Created by Shaoqiang.Zhang on 2017/1/9.
 */

@Data
public class ProductWorkItem {
    private String workNumber;
    private String workItemTypr;
    private String machineType;
    private String PCB_Code;
    private String formMaterialNumber;
    private String line;
    private String PWB_Number;
    private String cover;
    private String playOnLineTime;
    private String productStatus;

    public ProductWorkItem(String workNumber, String workItemTypr, String machineType, String PCB_Code, String formMaterialNumber, String line, String PWB_Number, String cover, String playOnLineTime, String productStatus) {
        this.workNumber = workNumber;
        this.workItemTypr = workItemTypr;
        this.machineType = machineType;
        this.PCB_Code = PCB_Code;
        this.formMaterialNumber = formMaterialNumber;
        this.line = line;
        this.PWB_Number = PWB_Number;
        this.cover = cover;
        this.playOnLineTime = playOnLineTime;
        this.productStatus = productStatus;
    }
}
