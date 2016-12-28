package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyItem;


import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class FeederSupplyModel extends BaseModel<ApiService> implements FeederSupplyContract.Model {
    public FeederSupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FeederSupplyItem>> getAllToBeSuppliedFeeders() {
        return getService().getAllToBeSuppliedFeeders().compose(RxsRxSchedulers.<List<FeederSupplyItem>>io_main());
    }
}
