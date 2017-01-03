package com.delta.smt.ui.storeroom.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ListWarning;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomContract {
    public interface View extends IView{
        void storeSuccess(String s);
        void storeFaild(String s);

    }
    public interface Model extends IModel{
        Observable<String>getStoreRoomSuccess();
    }
}
