package com.delta.app.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.app.api.ApiService;
import com.delta.app.base.BaseModel;
import com.delta.app.entity.FeederSupplyWarningItem;
import com.delta.app.entity.Result;

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
