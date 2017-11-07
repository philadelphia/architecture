package com.delta.app.entity.production_scan;

import com.delta.libs.adapter.TimeEntity;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ItemWarningInfo extends TimeEntity {

    private String work_order;
    private String line;
    private String side;
    private String product_name;
    private String product_name_main;

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_name_main() {
        return product_name_main;
    }

    public void setProduct_name_main(String product_name_main) {
        this.product_name_main = product_name_main;
    }
}
