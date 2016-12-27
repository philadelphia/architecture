package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;


import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceWarningFragmentModel extends BaseModel<ApiService> implements ProduceWarningFragmentContract.Model {

    public ProduceWarningFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ItemWarningInfo>> getItemWarningDatas() {
        return getService().getItemWarningDatas();
    }
}
