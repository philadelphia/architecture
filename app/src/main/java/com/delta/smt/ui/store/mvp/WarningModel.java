package com.delta.smt.ui.store.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class WarningModel extends BaseModel<ApiService> implements WarningContract.Model{
    public WarningModel(ApiService service) {
        super(service);
    }
}
