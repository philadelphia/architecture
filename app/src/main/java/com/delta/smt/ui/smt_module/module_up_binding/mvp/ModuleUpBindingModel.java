package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingModel extends BaseModel<ApiService> implements ModuleUpBindingContract.Model{

    public ModuleUpBindingModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ModuleUpBindingItem>> getAllModuleUpBindingItems() {
        List<ModuleUpBindingItem> dataList = new ArrayList<ModuleUpBindingItem>();
        dataList.add(new ModuleUpBindingItem("-","0353104700","03T021","-","2016082500"));
        dataList.add(new ModuleUpBindingItem("-","1512445A00","03T022","-","2016082500"));
        dataList.add(new ModuleUpBindingItem("-","15D2067A00","03T023","-","2016082500"));
        dataList.add(new ModuleUpBindingItem("-","1511508A00","03T024","-","2016082500"));
        return Observable.just(dataList);
        //return getService().getModuleUpBindingItems().compose(RxsRxSchedulers.<List<ModuleUpBindingItem>>io_main());
    }
}
