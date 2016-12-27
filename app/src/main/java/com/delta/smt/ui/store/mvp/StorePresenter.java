package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StorePresenter extends BasePresenter<StoreContract.Model,StoreContract.View> {
    @Inject
    public StorePresenter(StoreContract.Model model, StoreContract.View mView) {
        super(model, mView);
    }


}
