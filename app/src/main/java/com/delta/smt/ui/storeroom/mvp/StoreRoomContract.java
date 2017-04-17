package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomContract {
    public interface View extends IView{
        void storeSuccess(String s);
        void storeFaild(String s);
        void lightSuccsee(String log,String s);
        void lightfaild();
        void storageSuccsee();
        void storagefaild(String s);
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();

    }
    public interface Model extends IModel{
        Observable<String>getStoreRoomSuccess();
        Observable<Light> OnLight(String s);
        Observable<Success> PutInStorage(String s);
    }
}
