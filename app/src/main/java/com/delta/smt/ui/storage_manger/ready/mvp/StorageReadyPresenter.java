package com.delta.smt.ui.storage_manger.ready.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageReady;

import javax.inject.Inject;

import rx.functions.Action0;
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


    public void getStorageReady (String content){

        getModel().getStorageReady(content).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<StorageReady>>() {
              @Override
               public void call(Result<StorageReady> storageReadies) {

                  Log.e(TAG, "call: "+storageReadies.toString());
                  if (0==storageReadies.getCode()) {

                      getView().getStorageReadySuccess(storageReadies.getRows());
                      if(storageReadies.getRows().size()==0){
                          getView().showEmptyView();
                      }else {
                          getView().showContentView();
                      }
                  } else {
                      getView().showErrorView();
                      getView().getStorageReadyFailed(storageReadies.getMessage());

                  }
                 }
                }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getStorageReadyFailed(throwable.getMessage());
                getView().showErrorView();

            }
        });


    }

}
