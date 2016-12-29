package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.MantissaWarehouseReturn;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnModel extends BaseModel<ApiService> implements MantissaWarehouseReturnContract.Model{


    public MantissaWarehouseReturnModel(ApiService apiService) {
        super(apiService);
    }


    @Override
    public Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn() {
        return getService().getMantissaWarehouseReturn();
    }
}
