package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.AllQuery;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class WarningModel extends BaseModel<ApiService> implements WarningContract.Model{
    public WarningModel(ApiService service) {
        super(service);
    }

    @Override
    public Observable<AllQuery> getWarning() {
        return getService().getWarning().compose(RxsRxSchedulers.<AllQuery>io_main());
    }
}
