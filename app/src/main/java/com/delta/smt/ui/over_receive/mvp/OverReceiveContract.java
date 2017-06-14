package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public interface OverReceiveContract {
    interface View extends IView {

        void onSuccess(OverReceiveWarning data);

        void onFalied(OverReceiveWarning data);

        void onSuccessOverReceiveDebit(OverReceiveDebitResult data);

        void onFaliedOverReceiveDebit(OverReceiveDebitResult data);

        void onNetFailed(Throwable throwable);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<OverReceiveWarning> getAllOverReceiveItems();

        Observable<OverReceiveWarning> getOverReceiveItemsAfterSend(String str);

        Observable<OverReceiveDebitResult> getOverReceiveDebit();

    }
}
