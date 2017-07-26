package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class AcceptMaterialsModel extends BaseModel<ApiService> implements AcceptMaterialsContract.Model {
    public AcceptMaterialsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ItemAcceptMaterialDetail> getAcceptMaterialsItemDatas(String conditon) {


        return getService().getAcceptMaterialsItemDatas(conditon).compose(RxsRxSchedulers.<ItemAcceptMaterialDetail>io_main());
    }

    @Override
    public Observable<Result> commitSerialNumber(String condition) {
        return getService().commitSerialNumber(condition).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<Result> requestCloseLight(String condition) {
        return getService().requestCloseLight(condition).compose(RxsRxSchedulers.<Result>io_main());
    }
}
