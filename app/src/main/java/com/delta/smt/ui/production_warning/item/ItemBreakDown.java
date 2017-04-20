package com.delta.smt.ui.production_warning.item;

import com.delta.libs.adapter.TimeEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */

public class ItemBreakDown extends TimeEntity {

    @SerializedName("exception_name")
    private String title;

    @SerializedName("line")
    private String produce_line;

    @SerializedName("process")
    private String make_process;

    @SerializedName("slot")
    private String material_station;

    @SerializedName("child_exception_name")
    private String breakdown_info;

    @SerializedName("duration_time")
    private Long createTime;

    public ItemBreakDown() {
    }

    public ItemBreakDown(String title, String produce_line, String make_process, String material_station, String breakdown_info) {
        this.title = title;
        this.produce_line = produce_line;
        this.make_process = make_process;
        this.material_station = material_station;
        this.breakdown_info = breakdown_info;
    }




    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduce_line() {
        return produce_line;
    }

    public void setProduce_line(String produce_line) {
        this.produce_line = produce_line;
    }

    public String getMake_process() {
        return make_process;
    }

    public void setMake_process(String make_process) {
        this.make_process = make_process;
    }

    public String getMaterial_station() {
        return material_station;
    }

    public void setMaterial_station(String material_station) {
        this.material_station = material_station;
    }

    public String getBreakdown_info() {
        return breakdown_info;
    }

    public void setBreakdown_info(String breakdown_info) {
        this.breakdown_info = breakdown_info;
    }
}
