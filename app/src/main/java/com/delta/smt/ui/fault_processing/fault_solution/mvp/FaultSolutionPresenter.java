package com.delta.smt.ui.fault_processing.fault_solution.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.FaultSolutionMessage;

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
public class FaultSolutionPresenter extends BasePresenter<FaultSolutionContract.Model, FaultSolutionContract.View> {

    @Inject
    public FaultSolutionPresenter(FaultSolutionContract.Model model, FaultSolutionContract.View mView) {
        super(model, mView);
    }

    public void getDetailSolutionMessage(String fileName) {

        getModel().getDetailSolutionMessage(fileName).subscribe(new Action1<BaseEntity<String>>() {
            @Override
            public void call(BaseEntity<String> message) {

                if ("0".equals(message.getCode())) {
                    getView().onSuccess(message.getT());
                } else {

                    getView().getMessageFailed(message.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getMessageFailed(throwable.getMessage());
            }
        });
    }

    public void resolveFault(String faultContent) {

        getModel().resolveFault(faultContent).subscribe(new Action1<BaseEntity>() {
            @Override
            public void call(BaseEntity baseEntity) {

                if ("0".equals(baseEntity.getCode())) {
                    getView().resolveFaultSuccess(baseEntity.getMsg());
                } else {
                    getView().resolveFaultSuccess(baseEntity.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().resolveFaultSuccess(throwable.getMessage());
            }
        });

    }
}


