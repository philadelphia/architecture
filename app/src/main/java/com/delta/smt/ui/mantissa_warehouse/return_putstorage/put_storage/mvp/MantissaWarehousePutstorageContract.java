package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.BeginPut;
import com.delta.smt.entity.MantissaWarehousePutstorage;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

public interface MantissaWarehousePutstorageContract {

    public interface Model extends IModel {


        Observable<List<MantissaWarehousePutstorage>> getMantissaWarehousePutstorage();
        Observable<List<MantissaWarehousePutstorage>> getbeginput();

    }

    public interface View extends IView {

        void getSucess(List<MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getBeginSucess(List<MantissaWarehousePutstorage> mantissaWarehousePutstorages);
        void getFailed();
        void getBeginFailed();

    }
}
