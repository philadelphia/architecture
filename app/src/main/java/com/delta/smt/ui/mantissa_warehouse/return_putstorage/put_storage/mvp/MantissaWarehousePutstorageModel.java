package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

public class MantissaWarehousePutstorageModel extends BaseModel<ApiService> implements MantissaWarehousePutstorageContract.Model{


    public MantissaWarehousePutstorageModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage() {
        return getService().getMantissaWarehousePutstorage().compose(RxsRxSchedulers.<MantissaWarehousePutstorageResult>io_main());
    }

    @Override
    public Observable<List<MantissaWarehousePutstorageResult>> getbeginput() {
        return null;
    }
}
