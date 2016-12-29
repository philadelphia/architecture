package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehouseReturn;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public interface MantissaWarehouseReturnContract {

    interface Model extends IModel {


        Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn();

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseReturn> mantissaWarehouseReturns);
        void getFailed();

    }
}
