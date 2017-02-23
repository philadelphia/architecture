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

        Observable<Result<StorageDetails>> issureToWareh(String content);

        Observable<IssureToWarehFinishResult> issureToWarehFinish();

        Observable<Result<StorageDetails>> jumpMaterials();

        Observable<IssureToWarehFinishResult> sureCompleteIssue();

        Observable<Result> deduction();

    }

    interface View extends IView {

        void getSucess(Result<StorageDetails> storageDetailses);

        void getFailed(String message);

        void bindMaterialCarSucess(List<BindPrepCarIDByWorkOrderResult.RowsBean> data);

        void issureToWarehSuccess(Result<StorageDetails> rows);

        void issureToWarehFinishSuccess(String msg);

        void queryMaterailCar(List<MaterialCar.RowsBean> rows);

        void queryMaterailCarFailed(String msg);


        void bindMaterialCarFailed(String msg);


        void jumpMaterialsSuccess(Result<StorageDetails> result);

        void jumpMaterialsFailed(String message);

        void issureToWarehFailedWithoutJumpMaterials(String message);

        void issureToWarehFailedWithjumpMaterials(String message);

        void issureToWarehFinishFaildSure(String msg);

        void issureToWarehFinishFailedWithoutSure(String msg);

        void sureCompleteIssueSucess(String msg);

        void sureCompleteIssueFailed(String msg);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();


        void deductionFailed(String message);

        void deductionSuccess();
    }

}
