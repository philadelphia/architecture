package com.example.app.ui.main.mvp;


import com.example.commonlibs.mvp.IModel;
import com.example.commonlibs.mvp.IView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:41
 */


public interface MainContract {
    interface View extends IView {
        //更新
        //void checkExistUpdateDialog(Update update);
    }

    interface Model extends IModel {
        //更新
        //Observable<Update> getUpdate();
        //void download(Context context, String urlStr);
    }

}
