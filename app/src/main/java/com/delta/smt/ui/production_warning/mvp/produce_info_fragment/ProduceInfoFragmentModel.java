package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningContract;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceInfoFragmentModel extends BaseModel<ApiService> implements ProduceInfoFragmentContract.Model{
    public ProduceInfoFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ItemInfo>> getItemInfoDatas() {
        return getService().getItemInfoDatas();
    }
}
