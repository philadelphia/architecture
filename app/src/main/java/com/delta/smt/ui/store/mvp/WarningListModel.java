package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import java.util.List;

import rx.Observable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.delta.commonlibs.utils.RxsRxSchedulers.io_main;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListModel extends BaseModel<ApiService> implements WarningListContract.Model {

    public WarningListModel(ApiService service) {
        super(service);
    }


    @Override
    public Observable<String> getSuccessfulState() {
        return getService().getSuccessState().compose(RxsRxSchedulers.<String>io_main());
    }

    @Override
    public Observable<OutBound> getOutbound(String s) {
        return getService().outBound(s).compose(RxsRxSchedulers.<OutBound>io_main());
    }

    @Override
    public Observable<PcbNumber> getPcbNumber(String s) {
        return getService().getPcbNumber(s).compose(RxsRxSchedulers.<PcbNumber>io_main());
    }

    @Override
    public Observable<Success> getPcbSuccess(String s) {
        return getService().getPcbSuccess(s).compose(RxsRxSchedulers.<Success>io_main());
    }
}
