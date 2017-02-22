package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.Success;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
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
    public void StartWork(){
        getModel().startWork().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())) {
                    getView().showContentView();
                    getView().onStartWork(success.getMsg());
                } else {
                    getView().onFailed(success.getMsg());
                }}

    }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }

    public void OnGoing(){
        getModel().ongoing().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<OnGoing>() {
            @Override
            public void call(OnGoing onGoing) {
                if ("0".equals(onGoing.getCode())) {
                    getView().showContentView();
                    getView().ongoingSuccess(onGoing);
                } else {
                    getView().showContentView();
                    getView().ongoingFailed();
                }}

        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }

}
