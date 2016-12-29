package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehouseReturn;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@FragmentScope
public class MantissaWarehouseReturnPresenter extends BasePresenter<MantissaWarehouseReturnContract.Model,MantissaWarehouseReturnContract.View> {

    @Inject
    public MantissaWarehouseReturnPresenter(MantissaWarehouseReturnContract.Model model, MantissaWarehouseReturnContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehouseReturn(){

        getModel().getMantissaWarehouseReturn().subscribe(new Action1<List<MantissaWarehouseReturn>>() {
            @Override
            public void call(List<MantissaWarehouseReturn> mantissaWarehouseReturnes) {

                getView().getSucess(mantissaWarehouseReturnes);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed();

            }
        });

    }

}
