package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class SupplyModel extends BaseModel<ApiService> implements SupplyContract.Model {
    public SupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems() {
        return getService().getAllSupplyWorkItems().compose(RxsRxSchedulers.<List<FeederSupplyWarningItem>>io_main());
    }
}
