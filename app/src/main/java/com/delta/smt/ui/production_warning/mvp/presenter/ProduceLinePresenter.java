package com.delta.smt.ui.production_warning.mvp.presenter;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.mvp.contract.ProduceLineContract;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:42
 */

@ActivityScope
public class ProduceLinePresenter extends BasePresenter<ProduceLineContract.Model, ProduceLineContract.View> {


    @Inject
    public ProduceLinePresenter(ProduceLineContract.Model model, ProduceLineContract.View mView) {
        super(model, mView);
    }
    public void sumbitLine(String linename){

    }
    public void getProductionLineDatas(){

        getModel().getProductionLineDatas().subscribe(new Action1<List<ItemProduceLine>>() {
            @Override
            public void call(List<ItemProduceLine> itemProduceLines) {

                getView().getDataLineDatas(itemProduceLines);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFailed();
            }
        });
    }
}
