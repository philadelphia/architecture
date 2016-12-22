package com.delta.smt.ui.storage_manger.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.StorageReady;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

@ActivityScope
public class StorageReadyPresenter extends BasePresenter<StorageReadyContract.Model , StorageReadyContract.View> {


    public StorageReadyPresenter(StorageReadyContract.Model model, StorageReadyContract.View mView) {
        super(model, mView);
    }


    public void getStorageReady (String StorageName){

        getModel().getStorageReady(StorageName).subscribe(new Action1<StorageReady>() {
            @Override
            public void call(StorageReady storageReady) {

                getView().getStorageReadySucess();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable thorwable) {

                getView().getStorageReadyFailed();

            }
        });


    }

}
