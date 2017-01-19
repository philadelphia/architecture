package com.delta.smt.ui.fault_processing.fault_add.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.BaseEntity;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:38
 */

@ActivityScope
public class FaultProcessingAddPresenter extends BasePresenter<FaultProcessingAddContract.Model, FaultProcessingAddContract.View> {

    @Inject
    public FaultProcessingAddPresenter(FaultProcessingAddContract.Model model, FaultProcessingAddContract.View mView) {
        super(model, mView);
    }

    public void addSolution(String content) {

        getModel().addSolution(content).subscribe(new Action1<BaseEntity>() {
            @Override
            public void call(BaseEntity falutMesages) {

                if ("0".equals(falutMesages.getCode())) {
                    getView().addSucess(falutMesages.getMsg());
                } else {

                    getView().addFailed(falutMesages.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().addFailed(throwable.getMessage());
            }
        });
    }

}
