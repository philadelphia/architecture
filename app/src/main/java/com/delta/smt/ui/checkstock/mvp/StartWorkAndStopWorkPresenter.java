package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.InventoryExecption;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.Success;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StartWorkAndStopWorkPresenter extends BasePresenter<StartWorkAndStopWorkContract.Model,StartWorkAndStopWorkContract.View> {
    private boolean isTrue=true;
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
                    getView().showContentView();
                    getView().onFailed(success.getMsg());
                }}

    }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
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
                    for (int i=0;i<onGoing.getRows().getCompletedSubShelf().size();i++){
                        if (onGoing.getRows().getCompletedSubShelf().get(i).getStatus()==1){
                            getView().ongoingSuccess(onGoing.getRows().getCompletedSubShelf().get(i).getSubshelf(),onGoing.getRows().getCompletedSubShelf());
                            isTrue=false;
                        }
                    }
                    if (isTrue) {
                        getView().ongoingSuccess("", onGoing.getRows().getCompletedSubShelf());
                    }

                } else {
                    getView().showContentView();
                    getView().ongoingFailed();
                }}

        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
            }
        });
    }
    public void fetchInventoryException() {
        getModel().getInventoryException().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<InventoryExecption>() {
            @Override
            public void call(InventoryExecption success) {
                getView().showContentView();
                StringBuffer errorBuffer = new StringBuffer();
                errorBuffer.append("误差 \n");
                StringBuffer fewBuffer = new StringBuffer();
                if ("0".equals(success.getCode())) {
                    if (success.getMsg().contains("Success")) {
                        for (int i = 0; i < success.getRows().size(); i++) {
                            switch (success.getRows().get(i).getStatus()) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                    errorBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("\b 少料 \b").append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getRealCount());
                                    break;
                                case 4:
                                    errorBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("\b 多料 \b").append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getBoundCount());
                                    break;

                            }
                        }
                        fewBuffer.append(errorBuffer.toString());
                        getView().onInventoryException(fewBuffer.toString());
                    } else {
                        getView().onFailed(success.getMsg());
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                } catch (Exception e) {

                }
            }
        });
    }
        public void onEndSuccess(){
            getModel().OnEnd().doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    getView().showLoadingView();
                }
            }).subscribe(new Action1<Success>() {
                @Override
                public void call(Success success) {
                    if ("0".equals(success.getCode())){
                        getView().showContentView();
                        getView().onEndSucess();
                    }else {
                        getView().onFailed(success.getMsg());
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    try {
                        getView().showErrorView();
                        getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                    }catch (Exception e){

                    }
                }
            });
        }

}
