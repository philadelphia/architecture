package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StoreEntity;

import java.util.List;

import rx.Observable;



public interface StorageSelectContract {
    interface View extends IView{
        void onSucess(List<StoreEntity> wareHouses);
        void onFailed(String message);
        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
    interface Model extends IModel{
        Observable<Result<StoreEntity>> getStorageSelect();

    };

}
