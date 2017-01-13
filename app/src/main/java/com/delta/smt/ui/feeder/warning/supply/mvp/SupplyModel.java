package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class SupplyModel extends BaseModel<ApiService> implements SupplyContract.Model {
    public SupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems() {
//        return getService().getAllSupplyWorkItems().compose(RxsRxSchedulers.<List<FeederSupplyWarningItem>>io_main());
        List<FeederSupplyWarningItem> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            FeederSupplyWarningItem feederCheckInItem = new FeederSupplyWarningItem(1, "342", "A", "等待上模组",230000);
            feederCheckInItem.setEndTime(System.currentTimeMillis() + feederCheckInItem.getCountdown());
            feederCheckInItem.setId(i);
            dataList.add(feederCheckInItem);
        }

        return Observable.just(dataList);

    }
}
