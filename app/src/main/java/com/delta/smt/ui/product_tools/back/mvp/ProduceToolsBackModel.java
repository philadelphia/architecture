package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProductToolsBack;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackModel extends BaseModel<ApiService> implements ProduceToolsBackContract.Model{

    public ProduceToolsBackModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ProductToolsBack>> getProductToolsBack() {
        //TODO ZSQgetProductToolsBack
        List<ProductToolsBack> list=new ArrayList<>();
        list.add(new ProductToolsBack("1","20003034","23141224","刮刀","已归还"));
        list.add(new ProductToolsBack("2","20003034","23141224","钢网","未归还"));
        list.add(new ProductToolsBack("3","20003034","23141224","钢网","未归还"));
        list.add(new ProductToolsBack("4","20003034","23141224","钢网","未归还"));

        return Observable.just(list);
    }
}
