package com.delta.app.ui.feeder.warning.supply.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.app.entity.FeederSupplyWarningItem;
import com.delta.app.entity.Result;

import javax.inject.Inject;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@ActivityScope
public class SupplyPresenter extends BasePresenter<SupplyContract.Model, SupplyContract.View> {

    private RxErrorHandler rxErrorHandler;
    private static final String TAG = "SupplyPresenter";

    @Inject
    SupplyPresenter(SupplyContract.Model model, SupplyContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;
    }


    //获取Feeder备料排程
    public void getSupplyWorkItemList() {
        Log.i(TAG, "getSupplyWorkItemList: ");
        getModel().getSupplyWorkItemList().subscribe(new RxErrorHandlerSubscriber<Result<FeederSupplyWarningItem>>(rxErrorHandler) {
            @Override
            public void onStart() {
                super.onStart();
                getView().showLoadingView();
            }

            @Override
            public void onNext(Result<FeederSupplyWarningItem> feederSupplyWarningItemResult) {
                Log.i(TAG, "onNext: ");
                if (0 == feederSupplyWarningItemResult.getCode()) {
                    if (feederSupplyWarningItemResult.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().onGetSupplyWorkItemListSuccess(feederSupplyWarningItemResult.getRows());
                    }
                } else {
                    getView().onGetSupplyWorkItemListFailed(feederSupplyWarningItemResult.getMessage());
                    getView().showErrorView();

                }
            }
        });
    }

}
