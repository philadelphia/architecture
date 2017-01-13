package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import java.util.List;

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

                if("success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getSucess(mantissaWarehousePutstorageResult.getData());
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

    public void getBeginPut(){
        getModel().getbeginput().subscribe(new Action1<List<MantissaWarehousePutstorageResult>>() {
            @Override
            public void call(List<MantissaWarehousePutstorageResult> mantissaWarehousePutstorages) {
                getView().getBeginSucess(mantissaWarehousePutstorages);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getBeginFailed();
            }
        });
    }

}
