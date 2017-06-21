package com.delta.smt.entity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/6/20 19:22
 */


public class UploadMESParams {

    /**
     * work_order : 2311701657
     * side : A
     * mes_mode : 2311701657
     * is_feeder_buffer : A
     * feeding_list : [{"material_no":"1552458032","serial_no":"D201702241521454001","feeder_id":"KT8BD30662","slot":"02T07"}]
     * material_list : [{"material_no":"1552458032","serial_no":"D201702241521454003","slot":"03T08"}]
     */

    private String work_order;
    private String side;
    private String mes_mode;
    private String is_feeder_buffer;
    private List<UpLoadEntity.FeedingListBean> feeding_list;
    private List<UpLoadEntity.MaterialListBean> material_list;

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getMes_mode() {
        return mes_mode;
    }

    public void setMes_mode(String mes_mode) {
        this.mes_mode = mes_mode;
    }

    public String getIs_feeder_buffer() {
        return is_feeder_buffer;
    }

    public void setIs_feeder_buffer(String is_feeder_buffer) {
        this.is_feeder_buffer = is_feeder_buffer;
    }

    public List<UpLoadEntity.FeedingListBean> getFeeding_list() {
        return feeding_list;
    }

    public void setFeeding_list(List<UpLoadEntity.FeedingListBean> mFeeding_list) {
        feeding_list = mFeeding_list;
    }

    public List<UpLoadEntity.MaterialListBean> getMaterial_list() {
        return material_list;
    }

    public void setMaterial_list(List<UpLoadEntity.MaterialListBean> mMaterial_list) {
        material_list = mMaterial_list;
    }
}
