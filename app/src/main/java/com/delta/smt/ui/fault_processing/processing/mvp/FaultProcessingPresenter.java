package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.FalutMesage;

import java.util.List;

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

    public void getFaultProcessingMessages() {

        getModel().getFalutMessages().subscribe(new Action1<List<FalutMesage>>() {
            @Override
            public void call(List<FalutMesage> falutMesages) {

                getView().getFalutMessgeSucess(falutMesages);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFalutMessageFailed();
            }
        });
    }
}
