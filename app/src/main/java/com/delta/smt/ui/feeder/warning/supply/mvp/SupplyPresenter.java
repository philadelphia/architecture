package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@ActivityScope
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View>{
    @Inject
     SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView) {
        super(model, mView);
    }


    //获取Feeder备料排程
    public void getAllSupplyWorkItems(){
        getModel().getAllSupplyWorkItems().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<FeederSupplyWarningItem>>() {
            @Override
            public void call(Result<FeederSupplyWarningItem> feederSupplyWorkItems) {
                if ("0".equals(feederSupplyWorkItems.getCode())) {
                    if (feederSupplyWorkItems.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().onSuccess(feederSupplyWorkItems.getRows());
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().onFailed(throwable.getMessage());
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
