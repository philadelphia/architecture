package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Product_mToolsInfo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public class Produce_mToolsModel extends BaseModel<ApiService> implements Produce_mToolsContract.Model{
    public Produce_mToolsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<Product_mToolsInfo>> getProduct_mToolsInfo() {
        //TODO ZSQgetProduct_mToolsInfo
        List<Product_mToolsInfo> list=new ArrayList<>();
        list.add(new Product_mToolsInfo("1","32325432","钢网","D9-9001"));
        list.add(new Product_mToolsInfo("2","32325432","钢网","D2-9001"));
        list.add(new Product_mToolsInfo("3","32325432","钢网","D6-9001"));
        list.add(new Product_mToolsInfo("4","32325432","钢网","D8-9001"));

        return Observable.just(list);
    }
}
