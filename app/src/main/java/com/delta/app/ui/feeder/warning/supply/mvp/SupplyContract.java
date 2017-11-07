package com.delta.app.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.app.entity.FeederSupplyWarningItem;
import com.delta.app.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public interface SupplyContract {
    interface View extends IView {
        void onGetSupplyWorkItemListSuccess(List<FeederSupplyWarningItem> data);

        void onGetSupplyWorkItemListFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<Result<FeederSupplyWarningItem>> getSupplyWorkItemList();
    }
}
