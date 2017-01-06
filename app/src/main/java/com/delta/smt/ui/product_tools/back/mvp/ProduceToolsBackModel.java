package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackModel extends BaseModel<ApiService> implements ProduceToolsBackContract.Model{

    public ProduceToolsBackModel(ApiService apiService) {
        super(apiService);
    }

}
