package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OverReceiveItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public interface OverReceiveContract {
    interface View extends IView {

        public void onSuccess(List<OverReceiveItem> data);

        public void onFalied();

    }

    interface Model extends IModel {
        public Observable<List<OverReceiveItem>> getAllOverReceiveItems();
    }
}
