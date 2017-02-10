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

        public void onSuccess(OverReceiveWarning data);

        public void onFalied();

        public void onSuccessOverReceiveDebit(OverReceiveDebitResult data);

        public void onFaliedOverReceiveDebit();

    }

    interface Model extends IModel {
        public Observable<OverReceiveWarning> getAllOverReceiveItems();

        public Observable<OverReceiveWarning> getOverReceiveItemsAfterSend(String str);

        /*public Observable<OverReceiveWarning> getOverReceiveItemsAfterSendArrive(String str);*/

        public Observable<OverReceiveDebitResult > getOverReceiveDebit();

    }
}
