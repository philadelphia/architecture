package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */
@ActivityScope
public class StorageDetailsPresenter extends BasePresenter<StorageDetailsContract.Model,StorageDetailsContract.View> {

    @Inject
    public StorageDetailsPresenter(StorageDetailsContract.Model model, StorageDetailsContract.View mView) {
        super(model, mView);
    }

    public void getStorageDetails(String content){

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

}
