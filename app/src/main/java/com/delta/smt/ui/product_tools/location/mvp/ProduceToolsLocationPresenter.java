package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.JsonProductToolsLocation;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@ActivityScope
public class ProduceToolsLocationPresenter extends BasePresenter<ProduceToolsLocationContract.Model,ProduceToolsLocationContract.View> {

    @Inject
    public ProduceToolsLocationPresenter(ProduceToolsLocationContract.Model model, ProduceToolsLocationContract.View mView) {
        super(model, mView);
    }

    public void getLocation(String param){
        getModel().getLocationVerify(param).subscribe(new Action1<JsonProductToolsLocation>() {
            @Override
            public void call(JsonProductToolsLocation jsonProductToolsLocation) {
                getView().getLocation(jsonProductToolsLocation.getCode());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void getSubmitResoult(String param){
        getModel().getLocationSubmit(param).subscribe(new Action1<JsonProductToolsLocation>() {
            @Override
            public void call(JsonProductToolsLocation jsonProductToolsLocation) {
                getView().getSubmitResoult(jsonProductToolsLocation.getCode());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
