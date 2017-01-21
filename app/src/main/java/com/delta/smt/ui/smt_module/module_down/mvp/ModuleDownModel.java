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
        List<ModuleDownWarningItem.RowsBean> l = new ArrayList<>();
        ModuleDownWarningItem.RowsBean rb = new ModuleDownWarningItem.RowsBean();
        rb.setEnd_time("");
        rb.setFace("A");
        rb.setLine("1");
        rb.setWork_order("1234");
        l.add(rb);
        mi.setRows(l);
        return Observable.just(mi);*/
        return getService().getModuleDownWarningItems().compose(RxsRxSchedulers.<ModuleDownWarningItem>io_main());
    }
}
