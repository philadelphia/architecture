package com.delta.smt.ui.production_scan.binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public interface BindingContract {

    interface Model extends IModel {

        Observable<Result> uploadToMesFromProcessline(String value);
    }

    interface View extends IView {

        void getSuccess(Result result);

        void getFailed(Result message);

        void getFailed(Throwable throwable);

        void showLoadingView();
    }

}
