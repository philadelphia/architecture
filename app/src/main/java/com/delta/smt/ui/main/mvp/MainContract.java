package com.delta.smt.ui.main.mvp;

import android.content.Context;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Update;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:41
 */


public interface MainContract {
    interface View extends IView {
        //更新
        void checkExistUpdateDialog(Update update);
    }

    interface Model extends IModel {
        //更新
        Observable<Update> getUpdate();
        void download(Context context, String urlStr);
    }

}
