package com.delta.smt.ui.production_warning.mvp.produce_line;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;



@ActivityScope
public class ProduceLinePresenter extends BasePresenter<ProduceLineContract.Model, ProduceLineContract.View> {


    @Inject
    public ProduceLinePresenter(ProduceLineContract.Model model, ProduceLineContract.View mView) {
        super(model, mView);
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
