package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

public interface MantissaWarehousePutstorageContract {

    interface Model extends IModel {


        Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

        Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

        Observable<MantissaWarehousePutstorageBindTagResult> getbeginput(String str);

        Observable<MantissaWarehousePutstorageBindTagResult> bindMaterialCar(String str);

        Observable<MantissaWarehousePutstorageResult> getBingingLable(String str);

        Observable<MantissaWarehousePutstorageResult> getUpLocation(String str);

        Observable<MantissaWarehousePutstorageResult> getYesNext();

        Observable<MantissaWarehousePutstorageResult> getYesok();

        Observable<MantissaWarehousePutstorageResult> onclickBeginButton();

    }

    interface View extends IView {

        void getSucessUpdate(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getFailedUpdate(String message);

        void getSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getFailed(String message);

        void getYesNextSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getYesNextFailed(String message);

        void getYesokSucess();
        void getYesokFailed(String message);


        void getBeginSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorages);
        void getBeginFailed(String message);

        void bindMaterialCarSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorages);
        void bindMaterialCarFailed(String message);

        void getBingingLableSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getBingingLableFailed(String message);

        void getonclickBeginButtonSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getonclickBeginButtonFailed(String message);

        void getUpLocationSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getUpLocationFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
}
