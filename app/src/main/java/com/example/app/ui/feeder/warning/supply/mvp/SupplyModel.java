package com.example.app.ui.feeder.warning.supply.mvp;

import com.example.commonlibs.utils.RxsRxSchedulers;
import com.example.app.api.ApiService;
import com.example.app.base.BaseModel;
import com.example.app.entity.FeederSupplyWarningItem;
import com.example.app.entity.Result;

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
