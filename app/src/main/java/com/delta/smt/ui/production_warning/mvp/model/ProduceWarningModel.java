package com.delta.smt.ui.production_warning.mvp.model;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.mvp.contract.ProduceWarningContract;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningModel extends BaseModel<ApiService> implements ProduceWarningContract.Model {
    public ProduceWarningModel(ApiService apiService) {
        super(apiService);
    }
}