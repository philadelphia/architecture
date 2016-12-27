package com.delta.smt.ui.mantissa_warehouse.ready.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehouseReady;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseReadyContract {

    interface Model extends IModel {


        Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadies();

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseReady> mantissaWarehouseReadies);
        void getFailed();

    }


}
