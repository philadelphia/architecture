package com.delta.smt.ui.storage_manger.ready.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.StorageReady;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

@ActivityScope
public class StorageReadyPresenter extends BasePresenter<StorageReadyContract.Model , StorageReadyContract.View> {


    @Inject
    public StorageReadyPresenter(StorageReadyContract.Model model, StorageReadyContract.View mView) {
        super(model, mView);
    }


    public void getStorageReady (){

        getModel().getStorageReady().subscribe(new Action1<List<StorageReady>>() {
              @Override
               public void call(List<StorageReady> storageReadies) {

                    getView().getStorageReadySucess(storageReadies);

                 }
                },new Action1<Throwable>() {
            @Override
            public void call(Throwable thorwable) {

                getView().getStorageReadyFailed();

            }
        });


    }

}