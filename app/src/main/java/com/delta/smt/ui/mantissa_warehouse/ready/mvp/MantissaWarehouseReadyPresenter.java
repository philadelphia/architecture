package com.delta.smt.ui.mantissa_warehouse.ready.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.MantissaWarehouseReady;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@ActivityScope
public class MantissaWarehouseReadyPresenter extends BasePresenter<MantissaWarehouseReadyContract.Model, MantissaWarehouseReadyContract.View> {


    @Inject
    public MantissaWarehouseReadyPresenter(MantissaWarehouseReadyContract.Model model, MantissaWarehouseReadyContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehouseReadies() {

        getModel().getMantissaWarehouseReadies().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<MantissaWarehouseReady>() {
            @Override
            public void call(MantissaWarehouseReady mantissaWarehouseReady) {

                if ("0".equals(mantissaWarehouseReady.getCode())) {
                    if (mantissaWarehouseReady.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().getSuccess(mantissaWarehouseReady.getRows());
                    }
                } else {
                    getView().showContentView();
                    getView().getFailed(mantissaWarehouseReady.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFailed(throwable.getMessage());
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
