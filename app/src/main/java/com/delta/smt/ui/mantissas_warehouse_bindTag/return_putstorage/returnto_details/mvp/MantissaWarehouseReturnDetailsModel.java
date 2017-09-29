package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.entity.ManualDebitBean;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnDetailsModel extends BaseModel<ApiService> implements MantissaWarehouseReturnDetailsContract.Model{


    public MantissaWarehouseReturnDetailsModel(ApiService apiService) {
        super(apiService);
    }


    @Override
    public Observable<MantissaWarehouseReturnResult> getMantissaWarehouseReturn(String str) {
        return getService().getMantissaWarehouseReturn(str).compose(RxsRxSchedulers.<MantissaWarehouseReturnResult>io_main());
    }

    @Override
    public Observable<MantissaWarehouseReturnResult> getMaterialLocation(String str) {
        return getService().getMaterialLocation(str).compose(RxsRxSchedulers.<MantissaWarehouseReturnResult>io_main());
    }

    @Override
    public Observable<MantissaWarehouseReturnResult> getputinstrage(String str) {
        return getService().getputinstrage(str).compose(RxsRxSchedulers.<MantissaWarehouseReturnResult>io_main());
    }

    @Override
    public Observable<ManualDebitBean> getManualmaticDebit() {
        return getService().getManualmaticDebit().compose(RxsRxSchedulers.<ManualDebitBean>io_main());
    }

    @Override
    public Observable<ManualDebitBean> offLights() {
        return getService().offLights().compose(RxsRxSchedulers.<ManualDebitBean>io_main());
    }

    @Override
    public Observable<ManualDebitBean> deduction(String str) {
        return getService().getdeduction(str).compose(RxsRxSchedulers.<ManualDebitBean>io_main());
    }
}
