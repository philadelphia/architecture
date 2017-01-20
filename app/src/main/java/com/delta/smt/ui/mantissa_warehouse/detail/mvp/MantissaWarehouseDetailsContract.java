package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaCarResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public interface MantissaWarehouseDetailsContract {

    interface Model extends IModel {


        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(String str);

        Observable<MantissaCarResult> getFindCar(String str);

        Observable<MantissaCarResult> getBingingCar(String str);

        Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(String str);

        Observable<MantissaWarehouseDetailsResult> getMantissaWareOver();

    }

    interface View extends IView {

        void getSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getFailed(String message);

        void getBingingCarSucess(List<MantissaCarResult.MantissaCar> car);
        void getBingingCarFailed(String message);

        void getMantissaWarehouseputSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getMantissaWarehouseputFailed(String message);

        void getMantissaWareOverSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses);
        void getMantissaWareOverFailed(String message);

        void getFindCarSucess(List<MantissaCarResult.MantissaCar> car);
        void getFindCarFailed(String message);

    }
}
