package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.JsonProductBackRoot;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackPresenter extends BasePresenter<ProduceToolsBackContract.Model,ProduceToolsBackContract.View> {

    @Inject
    public ProduceToolsBackPresenter(ProduceToolsBackContract.Model model, ProduceToolsBackContract.View mView) {
        super(model, mView);
    }

    public void getData(String param){
        getModel().getProductToolsBack(param).subscribe(new Action1<JsonProductBackRoot>() {
            @Override
            public void call(JsonProductBackRoot jsonProductBackRoot) {
                getView().getData(jsonProductBackRoot);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFail();
            }
        });
    }
}
