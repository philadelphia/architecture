package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ProductToolsBack;

import java.util.List;

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

    public void getData(){
        getModel().getProductToolsBack().subscribe(new Action1<List<ProductToolsBack>>() {
            @Override
            public void call(List<ProductToolsBack> productToolsBacks) {
                getView().getData(productToolsBacks);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFail();
            }
        });
    }
}
