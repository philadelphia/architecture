package com.delta.smt.ui.production_warning.mvp.produce_line;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;



@ActivityScope
public class ProduceLinePresenter extends BasePresenter<ProduceLineContract.Model, ProduceLineContract.View> {


    @Inject
    public ProduceLinePresenter(ProduceLineContract.Model model, ProduceLineContract.View mView) {
        super(model, mView);
    }


    public void getProductionLineDatas(){

        getModel().getProductionLineDatas().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<ItemProduceLine>>() {
            @Override
            public void call(Result<ItemProduceLine> itemProduceLines) {
                if ("0".equals(itemProduceLines.getCode())) {
                    if (itemProduceLines.getRows().size() == 0) {
                        getView().showEmptyView();
                    }else {
                        getView().showContentView();
                        getView().getDataLineDatas(itemProduceLines.getRows());
                    }

                }else{
                    getView().getFailed(itemProduceLines.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
