package com.delta.smt.entity;

import java.util.IdentityHashMap;

/**
 * Created by Lin.Hou on 2017-01-15.
 */

public class ParameterOutBound {
    int id;
    int amount;

    public ParameterOutBound(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
