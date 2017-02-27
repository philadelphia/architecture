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
                    if(mantissaWarehouseReturnes.getRows().size() == 0 ){
                        getView().showEmptyView();
                    }else{
                        getView().getSucess(mantissaWarehouseReturnes.getRows());
                        getView().showContentView();
                    }

                }else{
                    getView().getFailed(mantissaWarehouseReturnes.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());
                getView().showErrorView();

            }
        });

    }

    public void getMaterialLocation(String str){

        getModel().getMaterialLocation(str).subscribe(new Action1<MantissaWarehouseReturnResult>() {
            @Override
            public void call(MantissaWarehouseReturnResult mantissaWarehouseReturnes) {

                if("Success".equals(mantissaWarehouseReturnes.getMsg())){
                    getView().getMaterialLocationSucess();
                }else{
                    getView().getMaterialLocationFailed(mantissaWarehouseReturnes.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());

            }
        });

    }

    public void getputinstrage(String str){

        getModel().getputinstrage(str).subscribe(new Action1<MantissaWarehouseReturnResult>() {
            @Override
            public void call(MantissaWarehouseReturnResult mantissaWarehouseReturnes) {

                if("Success".equals(mantissaWarehouseReturnes.getMsg())){
                    getView().getputinstrageSucess(mantissaWarehouseReturnes.getRows());
                }else{
                    getView().getputinstrageFailed( mantissaWarehouseReturnes.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getputinstrageFailed(throwable.getMessage());

            }
        });

    }

}
