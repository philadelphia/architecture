package com.delta.smt.ui.main.mvp;

import android.content.Context;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Update;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:42
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
@Inject
    public MainPresenter(MainContract.Model model, MainContract.View mView) {
        super(model, mView);
    }

    //更新检查
    public void checkUpdate() {
        getModel().getUpdate().subscribe(new Action1<Update>() {

            @Override
            public void call(Update update) {
                getView().checkExistUpdateDialog(update);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    //更新下载
    public void download(Context context, String urlStr) {
        long fileSize;
        getModel().download(context, urlStr);
    }
}
