package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public class ProduceWarningFragmentModel extends BaseModel<ApiService> implements ProduceWarningFragmentContract.Model {

    public ProduceWarningFragmentModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ItemWarningInfo>> getItemWarningDatas() {
        List<ItemWarningInfo> datas = new ArrayList<>();

        datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警", "产线：H13",
                "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                "400001",System.currentTimeMillis()+50000l,1));

        datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警",
                "产线：H13", "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                "400001",System.currentTimeMillis()+60000l,2));

        datas.add(new ItemWarningInfo("接料预警", "产线：H13",
                "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                "400001",System.currentTimeMillis()+40000l,3));


        return Observable.just(datas);
//        return getService().getItemWarningDatas();
    }
}
