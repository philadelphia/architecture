package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StoreRoomPresenter extends BasePresenter<StoreRoomContract.Model,StoreRoomContract.View>{
    @Inject
    public StoreRoomPresenter(StoreRoomContract.Model model, StoreRoomContract.View mView) {
        super(model, mView);
    }
    public void fatchStoreRoomSuccess(){
        getModel().getStoreRoomSuccess().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().storeSuccess(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().storeFaild(throwable.getMessage().toString());
            }
        });
    }
}
