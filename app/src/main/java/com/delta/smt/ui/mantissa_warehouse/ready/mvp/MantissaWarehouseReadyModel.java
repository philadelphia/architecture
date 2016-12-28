package com.delta.smt.ui.mantissa_warehouse.ready.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehouseReady;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseReadyModel extends BaseModel<ApiService> implements MantissaWarehouseReadyContract.Model{

    public MantissaWarehouseReadyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadies() {
        return getService().getMantissaWarehouseReadyDates();
    }
}
