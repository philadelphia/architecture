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


        Observable<MantissaWarehouseReady> getMantissaWarehouseReadies();

    }

    interface View extends IView {

        void getSuccess(List<MantissaWarehouseReady.RowsBean> mantissaWarehouseReadies);

        void getFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }


}
