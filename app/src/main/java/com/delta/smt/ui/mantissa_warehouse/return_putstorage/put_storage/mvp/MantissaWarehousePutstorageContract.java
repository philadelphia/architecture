package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

public interface MantissaWarehousePutstorageContract {

    public interface Model extends IModel {


        Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

        Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

        Observable<MantissaWarehousePutstorageResult> getbeginput();

        Observable<MantissaWarehousePutstorageResult> getBingingLable(String str);

        Observable<MantissaWarehousePutstorageResult> getUpLocation(String str);

        Observable<MantissaWarehousePutstorageResult> getYesNext();

        Observable<MantissaWarehousePutstorageResult> getYesok();

    }

    public interface View extends IView {

        void getSucessUpdate(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getFailedUpdate(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);

        void getSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);

        void getYesNextSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getYesNextFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);

        void getYesokSucess();
        void getYesokFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);


        void getBeginSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getBeginFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);

        void getBingingLableSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getBingingLableFailed(String message);

        void getUpLocationSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getUpLocationFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
}
