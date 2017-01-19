package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningContract;

import java.util.ArrayList;
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
    public Observable<ProduceWarning> getItemInfoDatas(String condition) {
/*        List<ItemInfo> datas = new ArrayList<>();
        datas.add(new ItemInfo("锡膏配送中", "产线：H13", "消息：锡膏即将配送到产线，请确认"));
        datas.add(new ItemInfo("替换钢网配送中", "产线：H13", "消息：替换钢网配送产线，请确认"));
        return Observable.just(datas);*/
        return getService().getItemInfoDatas(condition).compose(RxsRxSchedulers.<ProduceWarning>io_main());
    }

    @Override
    public Observable<Result> getItemInfoConfirm(String codition) {
        return getService().getItemInfoConfirm(codition).compose(RxsRxSchedulers.<Result>io_main());
    }
}
