package com.delta.smt.ui.storage_manger.ready.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageReady;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public interface StorageReadyContract {

    interface Model extends IModel{

        Observable<Result<StorageReady>> getStorageReady(String content);

    }

    interface View extends IView {

        void getStorageReadySucess(List<StorageReady> storageReadies);
        void getStorageReadyFailed(String message);

    }
}
