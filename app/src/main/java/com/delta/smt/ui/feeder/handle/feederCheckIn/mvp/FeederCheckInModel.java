package com.delta.smt.ui.feeder.handle.feederCheckIn.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public class FeederCheckInModel extends BaseModel<ApiService> implements FeederCheckInContract.Model{
    public FeederCheckInModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders() {
        return getService().getAllCheckedInFeeders().compose(RxsRxSchedulers.<List<FeederCheckInItem>>io_main());
    }
}
