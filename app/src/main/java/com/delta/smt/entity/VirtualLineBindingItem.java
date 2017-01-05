package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingItem {
    private String moduleID;
    private String virtualModuleID;

    public VirtualLineBindingItem(String moduleID, String virtualModuleID) {
        this.moduleID = moduleID;
        this.virtualModuleID = virtualModuleID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getVirtualModuleID() {
        return virtualModuleID;
    }

    public void setVirtualModuleID(String virtualModuleID) {
        this.virtualModuleID = virtualModuleID;
    }
}
