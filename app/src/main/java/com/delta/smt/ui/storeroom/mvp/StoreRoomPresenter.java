package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StoreRoomPresenter extends BasePresenter<StoreRoomContract.Model,StoreRoomContract.View>{
    @Inject
    public StoreRoomPresenter(StoreRoomContract.Model model, StoreRoomContract.View mView) {
        super(model, mView);
    }
}
