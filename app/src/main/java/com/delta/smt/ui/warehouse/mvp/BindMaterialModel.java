package com.delta.smt.ui.warehouse.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.bindmaterial.BindCarBean;
import com.delta.smt.entity.bindmaterial.BindLabelBean;
import com.delta.smt.entity.bindmaterial.FinishPda;
import com.delta.smt.entity.bindmaterial.ScanMaterialPanBean;
import com.delta.smt.entity.bindmaterial.StartStoreBean;
import com.delta.smt.entity.bindmaterial.WheatherBindStart;

import rx.Observable;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:27
 * @description :
 */

public class BindMaterialModel extends BaseModel<ApiService> implements BindMaterialContract.Model {

    public BindMaterialModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<WheatherBindStart> wheatherStart() {
        return getService().wheatherBindStart().compose(RxsRxSchedulers.<WheatherBindStart>io_main());
    }

    @Override
    public Observable<StartStoreBean> startStore() {
        return getService().startStore().compose(RxsRxSchedulers.<StartStoreBean>io_main());
    }

    @Override
    public Observable<BindCarBean> bindCar(String carName) {
        return getService().bindCar(carName).compose(RxsRxSchedulers.<BindCarBean>io_main());
    }

    @Override
    public Observable<ScanMaterialPanBean> scanMate(String materialPan) {
        return getService().scanMatePan(materialPan).compose(RxsRxSchedulers.<ScanMaterialPanBean>io_main());
    }

    @Override
    public Observable<BindLabelBean> bindLabel(String moveLabel) {
        return getService().bindLabel(moveLabel).compose(RxsRxSchedulers.<BindLabelBean>io_main());
    }

    @Override
    public Observable<FinishPda> finisdedPda() {
        return getService().finishedPda().compose(RxsRxSchedulers.<FinishPda>io_main());
    }
}
