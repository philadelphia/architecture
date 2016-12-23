package com.delta.smt.ui.production_warning.mvp.model;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.mvp.contact.ProductionWarningContract;
import com.delta.smt.ui.production_warning.mvp.view.ProductionBreakdownFragment;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProductionWarningModel extends BaseModel<ApiService> implements ProductionWarningContract.Model {
    public ProductionWarningModel(ApiService apiService) {
        super(apiService);
    }
}
