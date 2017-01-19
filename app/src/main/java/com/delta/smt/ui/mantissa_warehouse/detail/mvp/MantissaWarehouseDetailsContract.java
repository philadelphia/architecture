package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaCar;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseDetailsContract {

    interface Model extends IModel {


        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str);

        Observable<MantissaCar> getFindCar(String str);

        Observable<MantissaCar> getBingingCar(String str);

        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str);

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getFailed(String message);

        void getBingingCarSucess(MantissaCar car);
        void getBingingCarFailed(String message);

        void getMantissaWarehouseputSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getMantissaWarehouseputFailed(String message);

        void getFindCarSucess(MantissaCar car);
        void getFindCarFailed(String message);

    }
}
