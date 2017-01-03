package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.ListWarning;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
@ActivityScope
public class WarningListPresenter extends BasePresenter<WarningListContract.Model,WarningListContract.View>{
    @Inject
    public WarningListPresenter(WarningListContract.Model model, WarningListContract.View mView) {
        super(model, mView);
    }
    public void fatchListWarning(){
        getModel().getListWarning().subscribe(new Action1<List<ListWarning>>() {
            @Override
            public void call(List<ListWarning> listWarnings) {
                getView().onSucess(listWarnings);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }

    public void fathcSuccessState(){
        getModel().getSuccessfulState().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().onFailedState(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailedState(throwable.getMessage().toString());
            }
        });
    }
}
