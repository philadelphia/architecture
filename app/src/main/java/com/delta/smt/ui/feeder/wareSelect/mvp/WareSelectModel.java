package com.delta.smt.ui.feeder.wareSelect.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.WareHouse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class WareSelectModel extends BaseModel<ApiService> implements WareSelectContract.Model {
    public WareSelectModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<WareHouse>> getAllWareHouse() {
            List<WareHouse> dataList = new ArrayList<>();
            dataList.add(new WareHouse(1, "仓库A"));
            dataList.add(new WareHouse(2, "仓库B"));
            dataList.add(new WareHouse(3, "仓库C"));
            dataList.add(new WareHouse(4, "仓库D"));
            dataList.add(new WareHouse(5, "仓库E"));
            dataList.add(new WareHouse(6, "仓库F"));
            dataList.add(new WareHouse(7, "仓库G"));
            dataList.add(new WareHouse(8, "仓库H"));
            dataList.add(new WareHouse(9, "仓库I"));
            dataList.add(new WareHouse(10, "尾数仓"));
            dataList.add(new WareHouse(11, "Feeder缓冲区"));

            return Observable.just(dataList);
//        return getService().getAllWareHouse().compose(RxsRxSchedulers.<List<WareHouse>>io_main());
    }
}
