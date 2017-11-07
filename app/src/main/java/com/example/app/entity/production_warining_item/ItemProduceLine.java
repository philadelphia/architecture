package com.example.app.entity.production_warining_item;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ItemProduceLine {

    @SerializedName("production_line_name")
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

    @Override
    public String toString() {
        return "ItemProduceLine{" +
                "linename='" + linename + '\'' +
                ", checked=" + checked +
                '}';
    }
}
