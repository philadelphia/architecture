package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.SolutionMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:38
 */

@ActivityScope
public class FaultProcessingPresenter extends BasePresenter<FalutProcessingContract.Model, FalutProcessingContract.View> {

    private RxErrorHandler rxErrorHandler;

    @Inject
    public FaultProcessingPresenter(FalutProcessingContract.Model model, FalutProcessingContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;

    }

    public void getFaultProcessingMessages(String producelines) {

        getModel().getFalutMessages(producelines).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new RxErrorHandlerSubscriber<FaultMessage>(rxErrorHandler) {
            @Override
            public void onNext(FaultMessage falutMesages) {

                {
                    if ("0".equals(falutMesages.getCode())) {
                        if (falutMesages.getRows().size() == 0) {
                            getView().showEmptyView();
                        } else {
                            getView().showContentView();
                            getView().getFaultMessageSuccess(falutMesages);
                        }
                    } else {
                        getView().showContentView();
                        getView().getFaultMessageFailed(falutMesages.getMsg());
                    }
                }
            }
        });
//        getModel().getFalutMessages(producelines).doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                getView().showLoadingView();
//            }
//        }).subscribe(new Action1<FaultMessage>() {
//            @Override
//            public void call(FaultMessage falutMesages) {
//                if ("0".equals(falutMesages.getCode())) {
//                    if (falutMesages.getRows().size() == 0) {
//                        getView().showEmptyView();
//                    } else {
//                        getView().showContentView();
//                        getView().getFaultMessageSuccess(falutMesages);
//                    }
//                } else {
//                    getView().showContentView();
//                    getView().getFaultMessageFailed(falutMesages.getMsg());
//                }
//            }
//        }, new Action1<UnifyThrowable>() {
//            @Override
//            public void call(UnifyThrowable throwable) {
//                try {
//                    getView().showErrorView();
//                    getView().getFaultMessageFailed(throwable.getMessage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    }

    public void getSolution(String faultCode) {
        Map<String, String> maps = new HashMap<>();
        maps.put("faultCode", faultCode);
        faultCode = new Gson().toJson(maps);
        getModel().getSolutionMessage(faultCode).subscribe(new Action1<SolutionMessage>() {
            @Override
            public void call(SolutionMessage solutionMessage) {

                if ("0".equals(solutionMessage.getCode())) {
                    getView().getSolutionMessageSuccess(solutionMessage.getRows());
                } else {
                    getView().getFaultMessageFailed(solutionMessage.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().getFaultMessageFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
