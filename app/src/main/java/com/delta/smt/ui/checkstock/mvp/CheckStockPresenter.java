package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.CheckStock;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class CheckStockPresenter extends BasePresenter<CheckStockContract.Model,CheckStockContract.View> {
    @Inject
    public CheckStockPresenter(CheckStockContract.Model model, CheckStockContract.View mView) {
        super(model, mView);
    }
    public void fatchCheckStock(){
        getModel().getCheckStock().subscribe(new Action1<List<CheckStock>>() {
            @Override
            public void call(List<CheckStock> checkStocks) {
                getView().onSucess(checkStocks);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }
}
