package com.delta.smt.ui.production_warning.produce_line.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemProduceLine;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 12:49
 */


public class ProduceLineModel extends BaseModel<ApiService> implements ProduceLineContract.Model {


    public ProduceLineModel(ApiService apiService) {
        super(apiService);
    }


    @Override
    public Observable<Result<ItemProduceLine>> getProductionLineDatas() {
/*        List<ItemProduceLine> datas = new ArrayList<>();
        for (int mI = 1; mI < 16; mI++) {
            ItemProduceLine line = new ItemProduceLine("H" + mI, false);
            datas.add(line);
        }
        return Observable.just(datas);*/
        return getService().getLineDatas().compose(RxsRxSchedulers.<Result<ItemProduceLine>>io_main());
    }


}
