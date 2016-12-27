package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;

import javax.inject.Inject;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
public class ArrangePresenter extends BasePresenter<ArrangeContract.Model,ArrangeContract.View> {
    @Inject
    public ArrangePresenter(ArrangeContract.Model model, ArrangeContract.View mView) {
        super(model, mView);
    }
}
