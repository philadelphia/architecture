package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.MantissaWarehouseDetails;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@ActivityScope
public class MantissaWarehouseDetailsPresenter extends BasePresenter<MantissaWarehouseDetailsContract.Model,MantissaWarehouseDetailsContract.View> {

    @Inject
    public MantissaWarehouseDetailsPresenter(MantissaWarehouseDetailsContract.Model model, MantissaWarehouseDetailsContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehouseDetails(){

        getModel().getMantissaWarehouseDetails().subscribe(new Action1<List<MantissaWarehouseDetails>>() {
            @Override
            public void call(List<MantissaWarehouseDetails> mantissaWarehouseDetailses) {

                getView().getSucess(mantissaWarehouseDetailses);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed();

            }
        });

    }

}
