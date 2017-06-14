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

                if("Success".equals(mantissaWarehouseReturnes.getmessage())){
                    if(mantissaWarehouseReturnes.getRows().size() == 0 ){
                        getView().showEmptyView();
                    }else{
                        getView().getSucess(mantissaWarehouseReturnes.getRows());
                        getView().showContentView();
                    }

                }else{
                    getView().getFailed(mantissaWarehouseReturnes.getmessage());
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

    public void getMaterialLocation(String str){

        getModel().getMaterialLocation(str).subscribe(new Action1<MantissaWarehouseReturnResult>() {
            @Override
            public void call(MantissaWarehouseReturnResult mantissaWarehouseReturnes) {

                if("Success".equals(mantissaWarehouseReturnes.getmessage())){
                    getView().getMaterialLocationSucess();
                }else{
                    getView().getMaterialLocationFailed(mantissaWarehouseReturnes.getmessage());
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

    public void getputinstrage(String str){

        getModel().getputinstrage(str).subscribe(new Action1<MantissaWarehouseReturnResult>() {
            @Override
            public void call(MantissaWarehouseReturnResult mantissaWarehouseReturnes) {

                if("0".equals(mantissaWarehouseReturnes.getCode())){
                    getView().getputinstrageSucess(mantissaWarehouseReturnes.getRows());
                }else{
                    getView().getputinstrageFailed( mantissaWarehouseReturnes.getmessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().getputinstrageFailed(throwable.getMessage());
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
