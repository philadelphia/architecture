package com.delta.smt.ui.store.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreModel extends BaseModel<ApiService> implements  StoreContract.Model {

    public StoreModel(ApiService apiService) {
        super(apiService);
    }
}
