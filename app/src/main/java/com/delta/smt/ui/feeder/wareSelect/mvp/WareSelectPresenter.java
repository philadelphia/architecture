package com.delta.smt.ui.feeder.wareSelect.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class WareSelectPresenter extends BasePresenter<WareSelectContract.Model, WareSelectContract.View> {
    @Inject
     WareSelectPresenter(WareSelectContract.Model model, WareSelectContract.View mView) {
        super(model, mView);

    }

    public void fetchWareHouse(){
        getModel().getAllWareHouse().subscribe(new Action1<List<WareHouse>>() {
            @Override
            public void call(List<WareHouse> wareHouses) {
                getView().onSuccess(wareHouses);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
