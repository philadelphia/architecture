package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.Success;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeModel extends BaseModel<ApiService> implements ArrangeContract.Model {

    public ArrangeModel(ApiService service) {
        super(service);
    }


    @Override
    public Observable<AllQuery> getArrange() {
        return getService().getArrange().compose(RxsRxSchedulers.<AllQuery>io_main());
    }

    @Override
    public Observable<Success> getArrangeCloneLight(String s) {
        return getService().closeLights(s).compose(RxsRxSchedulers.<Success>io_main());
    }


}
