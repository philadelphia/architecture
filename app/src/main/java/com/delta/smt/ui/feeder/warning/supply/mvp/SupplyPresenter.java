package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@FragmentScope
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View>{
    @Inject
    public SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllSupplyWorkItems(){
        getModel().getAllSupplyWorkItems().subscribe(new Action1<List<FeederSupplyWarningItem>>() {
            @Override
            public void call(List<FeederSupplyWarningItem> feederSupplyWorkItems) {
                getView().onSuccess(feederSupplyWorkItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
