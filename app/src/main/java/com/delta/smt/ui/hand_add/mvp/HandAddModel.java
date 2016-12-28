package com.delta.smt.ui.hand_add.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddModel extends BaseModel<ApiService> implements HandAddContract.Model {

    public HandAddModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ItemHandAdd>> getItemHandAddDatas() {
        return getService().getItemHandAddDatas();
    }
}
