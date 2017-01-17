package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehouseReturnResult;

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

        getModel().getMantissaWarehouseReturn().subscribe(new Action1<MantissaWarehouseReturnResult>() {
            @Override
            public void call(MantissaWarehouseReturnResult mantissaWarehouseReturnes) {

                if("Success".equals(mantissaWarehouseReturnes.getMsg())){
                    getView().getSucess(mantissaWarehouseReturnes.getRows());
                }else{
                    getView().getFailed(mantissaWarehouseReturnes.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());

            }
        });

    }

}
