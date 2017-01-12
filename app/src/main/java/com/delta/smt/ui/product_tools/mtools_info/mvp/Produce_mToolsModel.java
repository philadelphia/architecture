package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Product_mToolsInfo;

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
        return getService().getProduct_mToolsInfo();
    }
}
