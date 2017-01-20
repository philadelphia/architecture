package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MaterialCar;
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

        Observable<MaterialCar> queryMaterialCar(String content);

        Observable<BindPrepCarIDByWorkOrderResult> bindMaterialCar(String content);

        Observable<Result<StorageDetails>>issureToWareh(String content);

        Observable<IssureToWarehFinishResult> issureToWarehFinish();

    }

    interface View extends IView {

        void getSucess(List<StorageDetails> storageDetailses);

        void getFailed(String message);

        void bindMaterialCarSucess(List<BindPrepCarIDByWorkOrderResult.DataBean> data);

        void issureToWarehSuccess(List<StorageDetails> rows);

        void issureToWarehFinishSuccess(String msg);

        void queryMaterailCar(String rows);

        void queryMaterailCarFailed(String msg);
    }

}
