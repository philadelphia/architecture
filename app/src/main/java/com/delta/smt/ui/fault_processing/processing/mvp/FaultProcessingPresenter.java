package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.FaultFilter;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.SolutionMessage;

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
                        if (falutMesages.getRows().getFailures().size() == 0) {
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

    }

    public void getSolution(String faultCode) {
        Map<String, String> maps = new HashMap<>();
        maps.put("exception_code", faultCode);
        getModel().getSolutionMessage(GsonTools.createGsonListString(maps)).subscribe(new Action1<SolutionMessage>() {
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

    public void getFaultFilterMessage() {

        getModel().getFaultFilterMessage().subscribe(new RxErrorHandlerSubscriber<FaultFilter>(rxErrorHandler) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(FaultFilter faultFilter) {
                if("0".equals(faultFilter.getCode())){
                    getView().getFaultFilterMessageFailed();
                }else{
                    getView().getFaultFilterMessageSuccess(faultFilter);
                }
            }
        });
    }
}
