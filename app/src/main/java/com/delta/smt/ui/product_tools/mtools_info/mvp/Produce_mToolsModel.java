package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProduct_mToolsRoot;
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
    public Observable<JsonProduct_mToolsRoot> getProduct_mToolsInfo(int pageSize,int pageCurrent,String condition_and_jigTypeID) {
        //TODO ZSQgetProduct_mToolsInfo

        return getService().getProduct_mToolsInfo(pageSize,pageCurrent,condition_and_jigTypeID).compose(RxsRxSchedulers.<JsonProduct_mToolsRoot>io_main());
    }
}
