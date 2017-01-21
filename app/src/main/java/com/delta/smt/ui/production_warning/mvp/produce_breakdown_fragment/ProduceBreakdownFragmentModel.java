package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProduceWarning;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceBreakdownFragmentModel extends BaseModel<ApiService> implements ProduceBreakdownFragmentContract.Model{

    public ProduceBreakdownFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ProduceWarning> getItemBreakdownDatas(String condition) {
/*        List<ItemBreakDown> datas = new ArrayList<>();
        datas.add(new ItemBreakDown("贴片机-卡料故障", "产线：H13", "制程：叠送一体机", "料站：06T022", "故障信息：卡料故障"));
        datas.add(new ItemBreakDown("贴片机-卷带故障", "产线：H13", "制程：贴片机", "料站：06T022", "故障信息：卷带故障"));
        return Observable.just(datas);*/
        return getService().getItemBreakDownDatas(condition).compose(RxsRxSchedulers.<ProduceWarning>io_main());
    }
}
