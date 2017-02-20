package com.delta.smt.ui.production_warning.mvp.produce_line;

import android.content.Context;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import javax.inject.Inject;

import rx.functions.Action1;


@ActivityScope
public class ProduceLinePresenter extends BasePresenter<ProduceLineContract.Model, ProduceLineContract.View> {

    private Context context;

    @Inject
    public ProduceLinePresenter(ProduceLineContract.Model model, ProduceLineContract.View mView, Context context) {
        super(model, mView);
        this.context = context;
    }


    public void getProductionLineDatas() {

        getModel().getProductionLineDatas().subscribe(new Action1<Result<ItemProduceLine>>() {
            @Override
            public void call(Result<ItemProduceLine> itemProduceLines) {

                if (itemProduceLines.getCode().equals("0")) {
                    getView().getDataLineDatas(itemProduceLines.getRows());
                } else {
                    getView().getFailed(itemProduceLines.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
