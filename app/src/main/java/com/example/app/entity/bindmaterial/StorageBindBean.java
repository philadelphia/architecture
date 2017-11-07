package com.example.app.entity.bindmaterial;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/12 11:26
 * @description :
 */

public class StorageBindBean {
    /**
     * materialNo : 4020108300
     * materialTotal : 3
     * moveLabel : N/A
     * shelf : R1A001
     */

    private String materialNo;
    private int materialTotal;
    private String moveLabel;
    private String shelf;

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public int getMaterialTotal() {
        return materialTotal;
    }

    public void setMaterialTotal(int materialTotal) {
        this.materialTotal = materialTotal;
    }

    public String getMoveLabel() {
        return moveLabel;
    }

    public void setMoveLabel(String moveLabel) {
        this.moveLabel = moveLabel;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
}
