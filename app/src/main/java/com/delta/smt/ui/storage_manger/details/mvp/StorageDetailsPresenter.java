package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
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

    public void getStorageDetails(){

        getModel().getStorageDetails().subscribe(new Action1<List<StorageDetails>>() {
            @Override
            public void call(List<StorageDetails> storageDetailses) {
                getView().getSucess(storageDetailses);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFailed();
            }
        });
    }

}
