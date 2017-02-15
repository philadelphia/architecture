package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class AcceptMaterialsModel extends BaseModel<ApiService> implements AcceptMaterialsContract.Model {
    public AcceptMaterialsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ItemAcceptMaterialDetail>> getItemDatas() {


        return null;
    }

    @Override
    public Observable<Result> commitBarode() {
        return null;
    }
}
