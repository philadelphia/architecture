package com.delta.smt.ui.feederCacheRegion.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

public class FeederCacheRegionModel extends BaseModel<ApiService> implements FeederCacheRegionContract.Model {
    public FeederCacheRegionModel(ApiService apiService) {
        super(apiService);
    }
}
