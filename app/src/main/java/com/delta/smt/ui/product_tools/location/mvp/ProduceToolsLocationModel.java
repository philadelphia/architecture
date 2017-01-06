package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationModel extends BaseModel<ApiService> implements ProduceToolsLocationContract.Model{
    public ProduceToolsLocationModel(ApiService apiService) {
        super(apiService);
    }
}
