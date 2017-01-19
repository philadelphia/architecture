package com.delta.smt.entity;

/**
 * Created by Shufeng.Wu on 2017/1/18.
 */

public class VirtualLineBindingItemNative {
    private String module_id;
    private String virtual_module_id;

    public VirtualLineBindingItemNative(String module_id, String virtual_module_id) {
        this.module_id = module_id;
        this.virtual_module_id = virtual_module_id;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getVirtual_module_id() {
        return virtual_module_id;
    }

    public void setVirtual_module_id(String virtual_module_id) {
        this.virtual_module_id = virtual_module_id;
    }
}
