package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;


import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public class FeederSupplyModel extends BaseModel<ApiService> implements FeederSupplyContract.Model {
    public FeederSupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(String workID) {
        return getService().getAllToBeSuppliedFeeders(workID).compose(RxsRxSchedulers.<Result<FeederSupplyItem>>io_main());
    }

    //获取feeder上模组时间
    @Override
    public Observable<Result<FeederSupplyItem>> getFeederInsertionToSlot(String serinal_num, String material_num, String quantity) {
        return getService().getFeederInsertionToSlot(serinal_num, material_num, quantity);
    }

    @Override
    public Observable<Result> upLoadFeederSupplyResult() {
        return getService().upLoadFeederSupplyResult().compose(RxsRxSchedulers.<Result>io_main());
    }


}
