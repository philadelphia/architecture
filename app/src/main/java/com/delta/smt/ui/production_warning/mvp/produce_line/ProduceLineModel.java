package com.delta.smt.ui.production_warning.mvp.produce_line;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 12:49
 */


public class ProduceLineModel extends BaseModel<ApiService> implements ProduceLineContract.Model {


    public ProduceLineModel(ApiService apiService) {
        super(apiService);
    }


    @Override
    public Observable<List<ItemProduceLine>> getProductionLineDatas() {
        return getService().getLineDatas();
    }

    @Override
    public Observable<String> sumbitLine() {
        return getService().sumbitLine();
    }
}
