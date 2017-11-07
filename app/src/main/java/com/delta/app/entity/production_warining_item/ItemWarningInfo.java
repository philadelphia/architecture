package com.delta.app.entity.production_warining_item;

import com.delta.libs.adapter.TimeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ItemWarningInfo extends TimeEntity {

    @SerializedName("alarmType")
    private String title;

    @SerializedName("line")
    private String productionline;

    @SerializedName("process")
    private String makeprocess;

    @SerializedName("alarmMessage")
    private String warninginfo;

    @SerializedName("workOrder")
    private String workcode;

    @SerializedName("side")
    private String face;

    @SerializedName("state")
    private String status;
    @SerializedName("unusedMaterials")
    private String unusedmaterials;

    @SerializedName("slot")
    private String materialstation;

    @SerializedName("remainTime")
    private Double time;
    private String connectMaterialCount;

    private int id;


    public ItemWarningInfo() {
    }

    public ItemWarningInfo(String title, String productionline,
                           String makeprocess, String warninginfo,
                           String countdown, Long endtime, int id) {
        this.title = title;
        this.productionline = productionline;
        this.makeprocess = makeprocess;
        this.warninginfo = warninginfo;
        this.id = id;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }


    public ItemWarningInfo(String title, String productionline, String workcode,
                           String face, String unusedmaterials,
                           String materialstation, String countdown, Long endtime, int id,
                           String connectMaterialCount) {
        this.title = title;
        this.productionline = productionline;
        this.workcode = workcode;
        this.face = face;
        this.unusedmaterials = unusedmaterials;
        this.materialstation = materialstation;
        this.id = id;
        this.connectMaterialCount = connectMaterialCount;
    }


    public String getConnectMaterialCount() {
        return connectMaterialCount;
    }

    public void setConnectMaterialCount(String connectMaterialCount) {
        this.connectMaterialCount = connectMaterialCount;
    }

    public int getId() {
        return id;
    }


    public void setId(int timeId) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductionline() {
        return productionline;
    }

    public void setProductionline(String productionline) {
        this.productionline = productionline;
    }

    public String getMakeprocess() {
        return makeprocess;
    }

    public void setMakeprocess(String makeprocess) {
        this.makeprocess = makeprocess;
    }

    public String getWarninginfo() {
        return warninginfo;
    }

    public void setWarninginfo(String warninginfo) {
        this.warninginfo = warninginfo;
    }

    public String getWorkcode() {
        return workcode;
    }

    public void setWorkcode(String workcode) {
        this.workcode = workcode;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnusedmaterials() {
        return unusedmaterials;
    }

    public void setUnusedmaterials(String unusedmaterials) {
        this.unusedmaterials = unusedmaterials;
    }

    public String getMaterialstation() {
        return materialstation;
    }

    public void setMaterialstation(String materialstation) {
        this.materialstation = materialstation;
    }
}
