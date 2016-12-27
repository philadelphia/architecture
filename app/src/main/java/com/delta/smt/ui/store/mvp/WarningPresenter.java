package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
public class WarningPresenter extends BasePresenter<WarningContract.Model,WarningContract.View> {
    @Inject
    public WarningPresenter(WarningContract.Model model, WarningContract.View mView) {
        super(model, mView);
    }
}
