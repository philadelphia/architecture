package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyWorkItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@FragmentScope
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View>{
    @Inject
    public SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllSupplyWorkItems(){
        getModel().getAllSupplyWorkItems().subscribe(new Action1<List<FeederSupplyWorkItem>>() {
            @Override
            public void call(List<FeederSupplyWorkItem> feederSupplyWorkItems) {
                getView().onSuccess(feederSupplyWorkItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
