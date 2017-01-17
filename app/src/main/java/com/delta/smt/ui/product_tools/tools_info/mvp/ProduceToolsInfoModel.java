package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProductToolsInfo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProduceToolsInfoModel extends BaseModel<ApiService> implements ProduceToolsInfoContract.Model{

    public ProduceToolsInfoModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ProductToolsInfo>> getProductToolsInfoItem() {
        //TODO ZSQgetProductToolsInfoItem

        List<ProductToolsInfo> list=new ArrayList<>();
        list.add(new ProductToolsInfo("1","11458754","钢网","A11-002","更多","待确认"));
        list.add(new ProductToolsInfo("2","11458756","钢网","A11-003","更多","待确认"));
        list.add(new ProductToolsInfo("3","11458756","钢网","A11-006","更多","待确认"));
        list.add(new ProductToolsInfo("4","11458756","钢网","A11-005","更多","待确认"));
        return Observable.just(list);
    }
}
