package com.delta.app.entity;


import com.delta.app.ui.product_tools.TimeSortUtils;

/**
 * Created by Shaoqiang.Zhang on 2017/1/9.
 */

public class ProductWorkItem implements Comparable{
    private String workNumber;
    private String mainBroad;
    private String littleBroad;
    private String PCB_Code;
    private String formMaterialNumber;
    private String line;
    private String PWB_Number;
    private String cover;
    private String playOnLineTime;
    private String productStatus;
    private String orderName;

    public ProductWorkItem(String orderName,String workNumber, String MainBroad, String LittleBroad, String PCB_Code, String formMaterialNumber, String line, String PWB_Number, String cover, String playOnLineTime, String productStatus) {
        this.orderName=orderName;
        this.workNumber = workNumber;
        this.mainBroad = MainBroad;
        this.littleBroad = LittleBroad;
        this.PCB_Code = PCB_Code;
        this.formMaterialNumber = formMaterialNumber;
        this.line = line;
        this.PWB_Number = PWB_Number;
        this.cover = cover;
        this.playOnLineTime = playOnLineTime;
        this.productStatus = productStatus;
    }

    public ProductWorkItem(String workNumber, String MainBroad, String LittleBroad, String PCB_Code, String formMaterialNumber, String line, String PWB_Number, String cover, String playOnLineTime, String productStatus) {

        this.workNumber = workNumber;
        this.mainBroad = MainBroad;
        this.littleBroad = LittleBroad;
        this.PCB_Code = PCB_Code;
        this.formMaterialNumber = formMaterialNumber;
        this.line = line;
        this.PWB_Number = PWB_Number;
        this.cover = cover;
        this.playOnLineTime = playOnLineTime;
        this.productStatus = productStatus;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getMainBroad() {
        return mainBroad;
    }

    public void setMainBroad(String mainBroad) {
        this.mainBroad = mainBroad;
    }

    public String getLittleBroad() {
        return littleBroad;
    }

    public void setLittleBroad(String littleBroad) {
        this.littleBroad = littleBroad;
    }

    public String getPCB_Code() {
        return PCB_Code;
    }

    public void setPCB_Code(String PCB_Code) {
        this.PCB_Code = PCB_Code;
    }

    public String getFormMaterialNumber() {
        return formMaterialNumber;
    }

    public void setFormMaterialNumber(String formMaterialNumber) {
        this.formMaterialNumber = formMaterialNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPWB_Number() {
        return PWB_Number;
    }

    public void setPWB_Number(String PWB_Number) {
        this.PWB_Number = PWB_Number;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlayOnLineTime() {
        return playOnLineTime;
    }

    public void setPlayOnLineTime(String playOnLineTime) {
        this.playOnLineTime = playOnLineTime;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public int compareTo(Object o) {
        ProductWorkItem otherItem= (ProductWorkItem) o;
        String otherTime=otherItem.getPlayOnLineTime();
        TimeSortUtils timeSortUnit=new TimeSortUtils(this.playOnLineTime,otherTime);
        return timeSortUnit.compare();
    }
}
