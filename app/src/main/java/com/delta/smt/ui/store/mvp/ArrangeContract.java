package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.ItemInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeContract {
    public   interface View extends IView{
        void onSucess(List<ItemInfo> wareHouses);
        void onFailed(String s);
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();
    }
    public interface Model extends IModel{
        Observable<AllQuery> getArrange();
    }
}
