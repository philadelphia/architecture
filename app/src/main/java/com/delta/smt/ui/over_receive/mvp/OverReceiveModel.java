package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.OverReceiveWarning;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveModel extends BaseModel<ApiService> implements OverReceiveContract.Model{
    public OverReceiveModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<OverReceiveWarning> getAllOverReceiveItems() {
        return getService().getOverReceiveItems().compose(RxsRxSchedulers.<OverReceiveWarning>io_main());
    }

    @Override
    public Observable<OverReceiveWarning> getOverReceiveItemsAfterSend(String str) {
        return getService().getOverReceiveItemSend(str).compose(RxsRxSchedulers.<OverReceiveWarning>io_main());
    }


}
