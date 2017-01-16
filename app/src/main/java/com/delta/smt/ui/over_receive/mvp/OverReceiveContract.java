package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.OverReceiveWarning;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public interface OverReceiveContract {
    interface View extends IView {

        public void onSuccess(OverReceiveWarning data);

        public void onFalied();

    }

    interface Model extends IModel {
        public Observable<OverReceiveWarning> getAllOverReceiveItems();

        public Observable<OverReceiveWarning> getOverReceiveItemsAfterSend(String str);
    }
}
