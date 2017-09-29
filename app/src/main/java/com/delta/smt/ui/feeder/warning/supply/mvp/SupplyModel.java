package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class SupplyModel extends BaseModel<ApiService> implements SupplyContract.Model {
    public SupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<FeederSupplyWarningItem>> getSupplyWorkItemList() {
        return getService().getSupplyWorkItemList().compose(RxsRxSchedulers.<Result<FeederSupplyWarningItem>>io_main());
    }
}
