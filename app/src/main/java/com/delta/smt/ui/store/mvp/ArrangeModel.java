package com.delta.smt.ui.store.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeModel extends BaseModel<ApiService> implements ArrangeContract.Model {

    public ArrangeModel(ApiService service) {
        super(service);
    }
}
