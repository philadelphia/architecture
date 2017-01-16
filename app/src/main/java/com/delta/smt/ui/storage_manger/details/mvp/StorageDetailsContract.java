package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public interface StorageDetailsContract {

    interface Model extends IModel {


        Observable<Result<StorageDetails>> getStorageDetails(String content);

    }

    interface View extends IView {

        void getSucess(List<StorageDetails> storageDetailses);
        void getFailed(String message);

    }

}
