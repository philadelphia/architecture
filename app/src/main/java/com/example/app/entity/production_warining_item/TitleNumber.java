package com.example.app.entity.production_warining_item;

/**
 * Created by Fuxiang.Zhang on 2016/12/26.
 */

public class TitleNumber {
    private int warning_number ;
    private int breakdown_number ;
    private int info_number ;

    public TitleNumber() {
    }

    public TitleNumber(int warning_number, int breakdown_number, int info_number) {
        this.warning_number = warning_number;
        this.breakdown_number = breakdown_number;
        this.info_number = info_number;
    }

    public int getInfo_number() {
        return info_number;
    }

    public void setInfo_number(int info_number) {
        this.info_number = info_number;
    }

    public int getWarning_number() {
        return warning_number;
    }

    public void setWarning_number(int warning_number) {
        this.warning_number = warning_number;
    }

    public int getBreakdown_number() {
        return breakdown_number;
    }

    public void setBreakdown_number(int breakdown_number) {
        this.breakdown_number = breakdown_number;
    }


}
