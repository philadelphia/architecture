package com.delta.smt.ui.production_warning.produce_warning_fragment.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;


import rx.Observable;

/**
 * Author Fuxiang.Zhang
 * Date   2016/12/23
 */

public class ProduceWarningFragmentModel extends BaseModel<ApiService> implements ProduceWarningFragmentContract.Model {

    public ProduceWarningFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ProduceWarning> getWarningItemList(String condition) {
        return getService().getItemWarningDatas(condition).compose(RxsRxSchedulers.<ProduceWarning>io_main());
    }

    @Override
    public Observable<Result> getItemWarningConfirm(String condition) {
        return getService().getItemWarningConfirm(condition).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<Result> getBarcodeInfo(String condition) {
        return getService().getBarcodeInfo(condition).compose(RxsRxSchedulers.<Result>io_main());
    }
}
