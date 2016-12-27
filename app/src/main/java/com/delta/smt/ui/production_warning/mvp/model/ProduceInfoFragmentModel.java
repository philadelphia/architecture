package com.delta.smt.ui.production_warning.mvp.model;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningContract;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceInfoFragmentModel extends BaseModel<ApiService> implements ProduceInfoFragmentContract.Model{
    public ProduceInfoFragmentModel(ApiService apiService) {
        super(apiService);
    }
}
