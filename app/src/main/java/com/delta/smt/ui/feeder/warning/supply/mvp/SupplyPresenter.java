package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
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
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View> {

    private RxErrorHandler rxErrorHandler;

    @Inject
    SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;
    }


    //获取Feeder备料排程
    public void getAllSupplyWorkItems() {
        getModel().getAllSupplyWorkItems().subscribe(new RxErrorHandlerSubscriber<Result<FeederSupplyWarningItem>>(rxErrorHandler) {
            @Override
            public void onStart() {
                super.onStart();
                getView().showLoadingView();
            }

            @Override
            public void onNext(Result<FeederSupplyWarningItem> feederSupplyWarningItemResult) {
                if ("0".equals(feederSupplyWarningItemResult.getCode())) {
                    if (feederSupplyWarningItemResult.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().onSuccess(feederSupplyWarningItemResult.getRows());
                    }
                } else {
                    getView().onFailed(feederSupplyWarningItemResult.getMessage());
                    getView().showErrorView();

                }
            }
        });
    }
}
