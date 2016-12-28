package com.delta.smt.ui.feeder.warning.checkin.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyWorkItem;

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
    public Observable<List<FeederSupplyWorkItem>> getAllCheckedInFeeders() {
        return getService().getAllCheckedInFeeders().compose(RxsRxSchedulers.<List<FeederSupplyWorkItem>>io_main());
    }
}
