package com.delta.smt.ui.feeder.warning.checkin.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class CheckInModel extends BaseModel<ApiService> implements CheckInContract.Model{
    public CheckInModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders() {
        return getService().getAllCheckedInFeeders().compose(RxsRxSchedulers.<Result<FeederCheckInItem>>io_main());
    }

    @Override
    public Observable<Result<FeederCheckInItem>> getFeederCheckInTime(String condition) {
        return getService().getFeederCheckInTime(condition).compose(RxsRxSchedulers.<Result<FeederCheckInItem>>io_main());
    }
}
