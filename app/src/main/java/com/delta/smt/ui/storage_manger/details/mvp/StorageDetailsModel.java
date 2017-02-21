package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsModel extends BaseModel<ApiService> implements StorageDetailsContract.Model {

    public StorageDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<StorageDetails>> getStorageDetails(String content) {
        return getService().getStorageDetails(content).compose(RxsRxSchedulers.<Result<StorageDetails>>io_main());
    }


    @Override
    public Observable<MaterialCar> queryMaterialCar(String content) {
        return getService().queryMaterialCar(content).compose(RxsRxSchedulers.<MaterialCar>io_main());
    }

    @Override
    public Observable<BindPrepCarIDByWorkOrderResult> bindMaterialCar(String content) {
        return getService().bindMaterialCar(content).compose(RxsRxSchedulers.<BindPrepCarIDByWorkOrderResult>io_main());
    }

    @Override
    public Observable<Result<StorageDetails>> issureToWareh(String content) {
        return getService().issureToWareh(content).compose(RxsRxSchedulers.<Result<StorageDetails>>io_main());
    }

    @Override
    public Observable<IssureToWarehFinishResult> issureToWarehFinish() {
        return getService().issureToWarehFinish().compose(RxsRxSchedulers.<IssureToWarehFinishResult>io_main());
    }

    @Override
    public Observable<Result<StorageDetails>> jumpMaterials() {
        return getService().jumpMaterials().compose(RxsRxSchedulers.<Result<StorageDetails>>io_main());
    }

    @Override
    public Observable<IssureToWarehFinishResult> sureCompleteIssue() {
        return getService().sureCompleteIssue().compose(RxsRxSchedulers.<IssureToWarehFinishResult>io_main());
    }
}
