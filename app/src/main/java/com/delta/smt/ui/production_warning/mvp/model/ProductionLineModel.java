package com.delta.smt.ui.production_warning.mvp.model;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.production_warning.mvp.contact.ProductionLineContract;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 12:49
 */


public class ProductionLineModel extends BaseModel<ApiService> implements ProductionLineContract.Model {

    public ProductionLineModel(ApiService apiService) {
        super(apiService);
    }
}
