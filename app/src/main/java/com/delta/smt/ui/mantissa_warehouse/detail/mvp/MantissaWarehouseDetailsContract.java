package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseDetailsContract {

    interface Model extends IModel {


        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str);

        Observable<Result<MaterialCar>> getFindCar(String str);

        Observable<Result<MaterialCar>> getBingingCar(String str);

        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str);

        Observable<IssureToWarehFinishResult> getMantissaWareOver(String s);


        Observable<Result> debit();

        Observable<Result<DebitData>> deduction(String mS);

        Observable getDebitDataList(String mS);

        Observable<Result> changecarshelf(String mGsonListString);
    }

    interface View extends IView {

        void getFailed(String message);

        void getBingingCarSuccess(Result<MaterialCar> car);

        void getBingingCarFailed(String message);

        void getMantissaWarehouseputSuccess(MantissaWarehouseDetailsResult mantissaWarehouseDetailses);

        void getMantissaWarehouseputFailed(String message);

        void getMantissaWareOverSuccess(IssureToWarehFinishResult issureToWarehFinishResult);

        void getMantissaWareOverFailed(String message);

        void getFindCarSucess(Result<MaterialCar> car);

        void getFindCarFailed(String message);

        void getMantissaWarehouseDetailsSucess(MantissaWarehouseDetailsResult mantissaWarehouseDetails);

        void getMantissaWarehouseDetailsFailed(String msg);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();


        void deductionSuccess(List<DebitData> mRows);

        void deductionFailed(String mMessage);

        void getDebitDataSuccess(List<DebitData> mDebitDataResult);

        void getDebitDataFailed(String mMessage);

        void changecarshelfSuccess(String mMessage);

        void changecarshelfFailed(String mMessage);
    }
}
