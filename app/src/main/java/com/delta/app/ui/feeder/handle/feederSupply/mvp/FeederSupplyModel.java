package com.delta.app.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.app.api.ApiService;
import com.delta.app.base.BaseModel;
import com.delta.app.entity.BaseEntity;
import com.delta.app.entity.DebitData;
import com.delta.app.entity.FeederSupplyItem;
import com.delta.app.entity.Result;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public class FeederSupplyModel extends BaseModel<ApiService> implements FeederSupplyContract.Model {
    public FeederSupplyModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<FeederSupplyItem>> getFeederList(String workID) {
        return getService().getFeederList(workID).compose(RxsRxSchedulers.<Result<FeederSupplyItem>>io_main());
    }

    //获取feeder上模组时间
    @Override
    public Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(String condition ) {
        return getService().getFeederInsertionToSlotTimeStamp(condition).compose(RxsRxSchedulers.<Result<FeederSupplyItem>>io_main());
    }

    @Override
    public Observable<Result> resetFeederSupplyStatus(String condition) {
        return getService().resetFeederSupplyStatus(condition).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<Result> jumpMES(String value) {
        return getService().jumpMES(value).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<Result<DebitData>> deductionAutomatically(String value) {
        return getService().deductionAutomatically(value).compose(RxsRxSchedulers.<Result<DebitData>>io_main());
    }

    @Override
    public Observable<Result<DebitData>> getUnDebitedItemList(String condition) {
        return getService().getUnDebitedItemList(condition).compose(RxsRxSchedulers.<Result<DebitData>>io_main());
    }




    @Override
    public Observable<BaseEntity<UpLoadEntity>> getUnUpLoadToMESList(String condition) {
        return getService().getUnUpLoadToMESList(condition).compose(RxsRxSchedulers.<BaseEntity<UpLoadEntity>>io_main());
    }

    //上传Feeder发料到MES
    @Override
    public Observable<Result> upLoadFeederSupplyToMES(String value) {
        return getService().upLoadFeederSupplyToMES(value).compose(RxsRxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<Result> lightOff(String argument) {
        return getService().lightOff(argument).compose(RxsRxSchedulers.<Result>io_main());
    }


}
