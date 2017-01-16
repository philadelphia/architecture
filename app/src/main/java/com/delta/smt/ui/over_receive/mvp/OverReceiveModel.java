package com.delta.smt.ui.over_receive.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.OverReceiveItem;

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
    public Observable<List<OverReceiveItem>> getAllOverReceiveItems() {
        return null;
                //getService().getOverReceiveItems().compose(RxsRxSchedulers.<List<OverReceiveItem>>io_main());
        // return getService().getOverReceiveItems().compose(RxsRxSchedulers.<List<OverReceiveItem>>io_main());
    }
}
