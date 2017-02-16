package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.Success;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StartWorkAndStopWorkPresenter extends BasePresenter<StartWorkAndStopWorkContract.Model,StartWorkAndStopWorkContract.View> {
    @Inject
    public StartWorkAndStopWorkPresenter(StartWorkAndStopWorkContract.Model model, StartWorkAndStopWorkContract.View mView) {
        super(model, mView);
    }

}
