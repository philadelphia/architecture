package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpModel extends BaseModel<ApiService> implements ModuleUpContract.Model{
    public ModuleUpModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleUpWarningItem> getAllModuleUpWarningItems() {
        /*ModuleUpWarningItem mi = new ModuleUpWarningItem();
        List<ModuleUpWarningItem.RowsBean> l = new ArrayList<>();
        ModuleUpWarningItem.RowsBean r = new ModuleUpWarningItem.RowsBean();
        r.setWork_order("100");
        r.setLine("1");
        r.setFace("A");
        r.setStart_time_plan("2017-01-19 15:00:00");
        l.add(r);
        mi.setRows(l);
        return Observable.just(mi);*/
        return getService().getModuleUpWarningItems().compose(RxsRxSchedulers.<ModuleUpWarningItem>io_main());
    }
}
