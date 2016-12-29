package com.delta.smt.ui.feeder.warning.checkin.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class CheckInModel extends BaseModel<ApiService> implements CheckInContract.Model{
    public CheckInModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders() {
        return getService().getAllCheckedInFeeders().compose(RxsRxSchedulers.<List<FeederCheckInItem>>io_main());
    }
}
