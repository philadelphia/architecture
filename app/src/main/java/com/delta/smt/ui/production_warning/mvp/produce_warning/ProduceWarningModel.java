package com.delta.smt.ui.production_warning.mvp.produce_warning;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProduceWarning;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningModel extends BaseModel<ApiService> implements ProduceWarningContract.Model {
    public ProduceWarningModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ProduceWarning> getTitleDatas(String condition) {
/*        TitleNumber mTitleNumber = new TitleNumber(2, 1, 1);

        return Observable.just(mTitleNumber);*/

        return getService().getTitleDatas(condition).compose(RxsRxSchedulers.<ProduceWarning>io_main());
    }
}
