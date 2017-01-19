package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.SolutionMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:38
 */

@ActivityScope
public class FaultProcessingPresenter extends BasePresenter<FalutProcessingContract.Model, FalutProcessingContract.View> {

    @Inject
    public FaultProcessingPresenter(FalutProcessingContract.Model model, FalutProcessingContract.View mView) {
        super(model, mView);
    }

    public void getFaultProcessingMessages(String producelines) {

        Map<String, String> maps = new HashMap<>();
        maps.put("lines", producelines);
        producelines = new Gson().toJson(maps);
        getModel().getFalutMessages(producelines).subscribe(new Action1<FaultMessage>() {
            @Override
            public void call(FaultMessage falutMesages) {

                if ("0".equals(falutMesages.getCode())) {
                    getView().getFalutMessgeSucess(falutMesages);
                } else {

                    getView().getFalutMessageFailed(falutMesages.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFalutMessageFailed(throwable.getMessage());
            }
        });
    }

    public void getSolution(String faultCode) {
        Map<String, String> maps = new HashMap<>();
        maps.put("faultCode", faultCode);
        faultCode = new Gson().toJson(maps);
        getModel().getSolutionMessage(faultCode).subscribe(new Action1<SolutionMessage>() {
            @Override
            public void call(SolutionMessage solutionMessage) {

                if ("0".equals(solutionMessage.getCode())) {
                    getView().getSolutionMessageSucess(solutionMessage.getRows());
                } else {
                    getView().getFalutMessageFailed(solutionMessage.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFalutMessageFailed(throwable.getMessage());
            }
        });
    }
}
