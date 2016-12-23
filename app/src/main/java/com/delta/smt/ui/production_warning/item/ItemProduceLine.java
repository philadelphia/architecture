package com.delta.smt.ui.production_warning.item;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ItemProduceLine {

    private String linename;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ItemProduceLine(String linename, boolean checked) {
        this.linename = linename;
        this.checked = checked;
    }

    public ItemProduceLine(String linename){
        this.linename=linename;
    }
    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }



}
