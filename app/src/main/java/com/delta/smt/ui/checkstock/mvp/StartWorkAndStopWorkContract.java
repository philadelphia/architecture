package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2017-02-10.
 */

public class StartWorkAndStopWorkContract {
    public  interface  View extends IView{
        void onFailed(String s);
        void onStartWork(String s);
        void ongoingSuccess(OnGoing s);
        void ongoingFailed();
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();

    }
    public interface Model extends IModel {
        Observable<Success>startWork();
        Observable<OnGoing>ongoing();
    }
}
