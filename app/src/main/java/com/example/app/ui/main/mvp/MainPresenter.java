package com.example.app.ui.main.mvp;


import com.example.commonlibs.di.scope.ActivityScope;
import com.example.commonlibs.mvp.BasePresenter;

import javax.inject.Inject;

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
    /*public void checkUpdate() {
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
    }*/

    //更新下载
    /*public void download(Context context, String urlStr) {
        long fileSize;
        getModel().download(context, urlStr);
    }*/
}
