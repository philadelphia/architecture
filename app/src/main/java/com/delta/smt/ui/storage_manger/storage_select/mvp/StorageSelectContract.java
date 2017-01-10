package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.StorageSelect;

import java.util.List;

import rx.Observable;



public interface StorageSelectContract {
    interface View extends IView{
        void onSucess(List<StorageSelect> wareHouses);
        void onFailed();
    }
    interface Model extends IModel{
        Observable<List<StorageSelect>> getStorageSelect();

    };

}
