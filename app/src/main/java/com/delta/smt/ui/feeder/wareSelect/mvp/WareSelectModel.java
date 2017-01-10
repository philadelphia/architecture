package com.delta.smt.ui.feeder.wareSelect.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class WareSelectModel extends BaseModel<ApiService> implements WareSelectContract.Model {
    public WareSelectModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<WareHouse>> getAllWareHouse() {
        return getService().getAllWareHouse().compose(RxsRxSchedulers.<List<WareHouse>>io_main());
    }
}
