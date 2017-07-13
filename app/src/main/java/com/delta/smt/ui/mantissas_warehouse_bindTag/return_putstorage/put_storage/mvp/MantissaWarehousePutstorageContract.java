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

        Observable<MantissaWarehousePutstorageBindTagResult> getBingingLable(String str);

        Observable<MantissaWarehousePutstorageBindTagResult> getUpLocation(String str);

        Observable<MantissaWarehousePutstorageBindTagResult> onlickSubmit();

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

        void getBingingLableSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorages);
        void getBingingLableFailed(String message);

        void getonclickBeginButtonSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getonclickBeginButtonFailed(String message);

        void getUpLocationSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorages);
        void getUpLocationFailed(String message);

        void onlickSubmitSucess(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorages);
        void onlickSubmitFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
}
