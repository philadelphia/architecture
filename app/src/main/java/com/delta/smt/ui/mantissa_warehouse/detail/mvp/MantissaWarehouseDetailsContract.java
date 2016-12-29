package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehouseDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseDetailsContract {

    interface Model extends IModel {


        Observable<List<MantissaWarehouseDetails>> getMantissaWarehouseDetails();

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getFailed();

    }
}
