package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MaterialCar;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseDetailsContract {

    interface Model extends IModel {


        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str);

        Observable<MaterialCar> getFindCar(String str);

        Observable<MaterialCar> getBingingCar(String str);

        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str);

        Observable<IssureToWarehFinishResult> getMantissaWareOver();

    }

    interface View extends IView {

        void getFailed(String message);

        void getBingingCarSucess(MaterialCar car);

        void getBingingCarFailed(String message);

        void getMantissaWarehouseputSucess(MantissaWarehouseDetailsResult mantissaWarehouseDetailses);

        void getMantissaWarehouseputFailed(String message);

        void getMantissaWareOverSucess(IssureToWarehFinishResult issureToWarehFinishResult);

        void getMantissaWareOverFailed(String message);

        void getFindCarSucess(MaterialCar car);

        void getFindCarFailed(String message);

        void getMantissaWarehouseDetailsSucess(MantissaWarehouseDetailsResult mantissaWarehouseDetails);

        void getMantissaWarehouseDetailsFailed(String msg);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
}
