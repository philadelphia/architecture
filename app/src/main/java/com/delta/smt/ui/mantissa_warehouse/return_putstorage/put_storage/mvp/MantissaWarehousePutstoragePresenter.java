package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehousePutstorage;

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

        getModel().getMantissaWarehousePutstorage().subscribe(new Action1<List<MantissaWarehousePutstorage>>() {
            @Override
            public void call(List<MantissaWarehousePutstorage> mantissaWarehousePutstorage) {

                getView().getSucess(mantissaWarehousePutstorage);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed();

            }
        });

    }

}
