package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;


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
    public Observable<List<com.delta.smt.entity.ItemInfo>> getWarning() {
        return getService().getWarning().compose(RxsRxSchedulers.<List<com.delta.smt.entity.ItemInfo>>io_main());
    }
}
