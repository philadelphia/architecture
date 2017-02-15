package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;

/**
 * Created by Lin.Hou on 2017-02-10.
 */

public class StartWorkAndStopWorkContract {
    public  interface  View extends IView{
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();

    }
    public interface Model extends IModel {}
}
