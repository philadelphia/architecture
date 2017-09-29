package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */

public class ModuleUpModel extends BaseModel<ApiService> implements ModuleUpContract.Model{
    public ModuleUpModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ModuleUpWarningItem>> getAllModuleUpWarningItems() {
        /*ModuleUpWarningItem mi = new ModuleUpWarningItem();
        mi.setCode("0");
        mi.setMsg("success");
        List<ModuleUpWarningItem.RowsBean> l = new ArrayList<>();
        ModuleUpWarningItem.RowsBean r = new ModuleUpWarningItem.RowsBean();
        r.setWork_order("100");
        r.setStatus("204");
        r.setSide("B");
        r.setProduct_name_main("A");
        r.setProduct_name("A");
        r.setLine_name("H111");
        r.setOnline_plan_start_time("2017-02-19 15:00:00");
        l.add(r);
        mi.setRows(l);
        return Observable.just(mi);*/
        return getService().getModuleUpWarningItems().compose(RxsRxSchedulers.<Result<ModuleUpWarningItem>>io_main());
    }
}
