package com.delta.smt.ui.checkstock.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockModel extends BaseModel<ApiService> implements CheckStockContract.Model{

    public CheckStockModel(ApiService service) {
        super(service);
    }
}
