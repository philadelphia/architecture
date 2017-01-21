package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */
@ActivityScope
public class StorageDetailsPresenter extends BasePresenter<StorageDetailsContract.Model, StorageDetailsContract.View> {

    @Inject
    public StorageDetailsPresenter(StorageDetailsContract.Model model, StorageDetailsContract.View mView) {
        super(model, mView);
    }

    public void getStorageDetails(String content) {

        getModel().getStorageDetails(content).subscribe(new Action1<Result<StorageDetails>>() {
            @Override
            public void call(Result<StorageDetails> storageDetailses) {
                if ("0".equals(storageDetailses.getCode())) {
                    getView().getSucess(storageDetailses.getRows());
                } else {
                    getView().getFailed(storageDetailses.getMessage());

                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFailed(throwable.getMessage());
            }
        });
    }

    public void queryMaterailCar(String content) {
        getModel().queryMaterialCar(content).subscribe(new Action1<MaterialCar>() {
            @Override
            public void call(MaterialCar materialCar) {

                if ("success".equalsIgnoreCase(materialCar.getMsg())) {
                    getView().queryMaterailCar(materialCar.getRows().get(0).getMsg());
                } else {
                    getView().queryMaterailCarFailed(materialCar.getRows().get(0).getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFailed(throwable.getMessage());
            }
        });
    }

    public void bindBoundPrepCar(String content) {

        getModel().bindMaterialCar(content).subscribe(new Action1<BindPrepCarIDByWorkOrderResult>() {
            @Override
            public void call(BindPrepCarIDByWorkOrderResult result) {

                if ("success".equalsIgnoreCase(result.getMsg())) {
                    getView().bindMaterialCarSucess(result.getData());
                } else {
                    getView().bindMaterialCarFailed(result.getData().get(0).getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

               // getView().getFailed(throwable.getMessage());
            }
        });
    }

    public void issureToWareh(String content) {
        getModel().issureToWareh(content).subscribe(new Action1<Result<StorageDetails>>() {
            @Override
            public void call(Result<StorageDetails> issureToWarehResult) {

                if ("success".equalsIgnoreCase(issureToWarehResult.getMessage())) {
                    getView().issureToWarehSuccess(issureToWarehResult.getRows());
                } else {
                    getView().getFailed(issureToWarehResult.getRows().get(0).getMsg() + "");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFailed(throwable.getMessage());
            }
        });
    }

    public void issureToWarehFinish() {

        getModel().issureToWarehFinish().subscribe(new Action1<IssureToWarehFinishResult>() {
            @Override
            public void call(IssureToWarehFinishResult issureToWarehFinishResult) {

                if ("success".equalsIgnoreCase(issureToWarehFinishResult.getMsg())) {
                    getView().issureToWarehFinishSuccess(issureToWarehFinishResult.getMsg());
                } else {
                    getView().getFailed(issureToWarehFinishResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());
            }
        });
    }

}
