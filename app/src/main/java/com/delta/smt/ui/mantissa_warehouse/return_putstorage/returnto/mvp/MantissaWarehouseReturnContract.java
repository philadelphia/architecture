package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehouseReturnResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public interface MantissaWarehouseReturnContract {

    interface Model extends IModel {


        Observable<MantissaWarehouseReturnResult> getMantissaWarehouseReturn();

        Observable<MantissaWarehouseReturnResult> getMaterialLocation(String str);

        Observable<MantissaWarehouseReturnResult> getputinstrage(String str);

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturns);
        void getFailed(String message);

        void getMaterialLocationSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturns);
        void getMaterialLocationFailed(String message);

        void getputinstrageSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturns);
        void getputinstrageFailed(String message);

    }
}
