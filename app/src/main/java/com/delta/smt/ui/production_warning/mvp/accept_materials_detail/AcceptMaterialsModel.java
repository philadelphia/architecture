package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class AcceptMaterialsModel extends BaseModel<ApiService> implements AcceptMaterialsContract.Model {
    public AcceptMaterialsModel(ApiService apiService) {
        super(apiService);
    }
}
