package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownWarningItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownModel extends BaseModel<ApiService> implements ModuleDownContract.Model{
    public ModuleDownModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleDownWarningItem> getAllModuleDownWarningItems() {

        /*ModuleDownWarningItem mi = new ModuleDownWarningItem();
        mi.setMsg("success");
        mi.setCode("0");
        List<ModuleDownWarningItem.RowsBean> l = new ArrayList<>();
        ModuleDownWarningItem.RowsBean rb = new ModuleDownWarningItem.RowsBean();
        rb.setWork_order("1234");
        rb.setProduct_name_main("A");
        rb.setProduct_name("a");
        rb.setLine_name("H1");
        rb.setSide("B");
        rb.setStatus("等待下模组");
        rb.setUnplug_mod_actual_finish_time("2017-02-17 12:00:00");
        l.add(rb);
        mi.setRows(l);
        return Observable.just(mi);*/
        return getService().getModuleDownWarningItems().compose(RxsRxSchedulers.<ModuleDownWarningItem>io_main());
    }
}
