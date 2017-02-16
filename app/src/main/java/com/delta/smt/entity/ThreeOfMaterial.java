package com.delta.smt.entity;

/**
 * Created by Lin.Hou on 2017-02-08.
 */

public class ThreeOfMaterial {

    private String deltaMaterialNumber;
    private String pcbCode;
    private String dataCode;
    private String count;

    public ThreeOfMaterial(String deltaMaterialNumber, String pcbCode, String dataCode, String count) {
        this.deltaMaterialNumber = deltaMaterialNumber;
        this.pcbCode = pcbCode;
        this.dataCode = dataCode;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDeltaMaterialNumber() {
        return deltaMaterialNumber;
    }

    public void setDeltaMaterialNumber(String deltaMaterialNumber) {
        this.deltaMaterialNumber = deltaMaterialNumber;
    }

    public String getPcbCode() {
        return pcbCode;
    }

    public void setPcbCode(String pcbCode) {
        this.pcbCode = pcbCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }
}
