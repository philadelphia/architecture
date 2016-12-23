package com.delta.smt.ui.feederCacheRegion.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

public class FeederCacheRegionModel extends BaseModel<ApiService> implements FeederCacheRegionContract.Model {
    public FeederCacheRegionModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<WareHouse>> getAllWareHouse() {
        return getService().getAllWareHouse().compose(RxsRxSchedulers.<List<WareHouse>>io_main());
    }
}
