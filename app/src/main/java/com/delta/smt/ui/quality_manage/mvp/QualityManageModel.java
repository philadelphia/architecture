package com.delta.smt.ui.quality_manage.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.QualityManage;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2017/4/18.
 */

public class QualityManageModel extends BaseModel<ApiService> implements QualityManageContract.Model{
    public QualityManageModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<QualityManage> getQualityList(String str) {
        return getService().getQualityList(str).compose(RxsRxSchedulers.<QualityManage>io_main());
    }

}
