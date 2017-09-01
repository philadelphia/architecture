package com.delta.smt.ui.production_scan.binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */
@ActivityScope
public class BindingPresenter extends BasePresenter<BindingContract.Model, BindingContract.View> {

    private RxErrorHandler rxErrorHandler;

    @Inject
    public BindingPresenter(BindingContract.Model model, BindingContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void uploadToMesFromProcessline(String value) {

        getModel().uploadToMesFromProcessline(value).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {

                if (0 == result.getCode()) {
                    getView().getSuccess(result);
                } else {
                    getView().getFailed(result);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFailed(throwable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
