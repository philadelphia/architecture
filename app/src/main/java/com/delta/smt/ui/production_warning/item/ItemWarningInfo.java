package com.delta.smt.ui.production_warning.item;

import com.delta.smt.entity.CountDownEntity;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ItemWarningInfo extends CountDownEntity{
    private String title;
    private String productionline;
    private String makeprocess;
    private String warninginfo;


    public ItemWarningInfo(){}

    public ItemWarningInfo(String title,String productionline,
                           String makeprocess,String warninginfo,
                           Long countdown,Long endtime,int id){
        this.title=title;
        this.productionline=productionline;
        this.makeprocess=makeprocess;
        this.warninginfo=warninginfo;
        this.countdown=countdown;
        this.endTime=endtime;
        this.id=id;
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


}
