package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@FragmentScope
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View>{
    @Inject
     SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllSupplyWorkItems(){
        getModel().getAllSupplyWorkItems().subscribe(new Action1<Result<FeederSupplyWarningItem>>() {
            @Override
            public void call(Result<FeederSupplyWarningItem> feederSupplyWorkItems) {
                getView().onSuccess(feederSupplyWorkItems.getRows());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });
    }
}
