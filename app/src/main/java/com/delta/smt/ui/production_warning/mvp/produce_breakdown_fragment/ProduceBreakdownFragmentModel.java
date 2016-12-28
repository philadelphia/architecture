package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceBreakdownFragmentModel extends BaseModel<ApiService> implements ProduceBreakdownFragmentContract.Model{

    public ProduceBreakdownFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ItemBreakDown>> getItemBreakdownDatas() {
        return getService().getItemBreakDownDatas();
    }
}
