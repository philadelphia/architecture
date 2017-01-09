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
}
