package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

@FragmentScope
public class MantissaWarehousePutstoragePresenter extends BasePresenter<MantissaWarehousePutstorageContract.Model,MantissaWarehousePutstorageContract.View> {

    @Inject
    public MantissaWarehousePutstoragePresenter(MantissaWarehousePutstorageContract.Model model, MantissaWarehousePutstorageContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehousePutstorage(){

        getModel().getMantissaWarehousePutstorage().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());
            }
        });

    }

    public void getUpdate(){

        getModel().getMantissaWarehousePutstorageUpdate().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getSucessUpdate(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getFailedUpdate(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailedUpdate(throwable.getMessage());
            }
        });
    }

    public void getBeginPut(){

        getModel().getbeginput().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getBeginSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getBeginFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getBeginFailed(throwable.getMessage());
            }
        });

    }

    public void getBindingLabel(String str){

        getModel().getBingingLable(str).subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getBeginSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getBeginFailed(mantissaWarehousePutstorageResult.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getBeginFailed(throwable.getMessage());

            }
        });

    }


}
