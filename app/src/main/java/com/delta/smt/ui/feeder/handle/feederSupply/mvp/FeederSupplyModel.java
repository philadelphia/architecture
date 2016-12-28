package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyWorkItem;

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
    public Observable<List<FeederSupplyWorkItem>> getAllSupplyWorkItems() {
        return getService().getAllSupplyWorkItems().compose(RxsRxSchedulers.<List<FeederSupplyWorkItem>>io_main());
    }
}
