package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownModel extends BaseModel<ApiService> implements ModuleDownContract.Model{
    public ModuleDownModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ModuleDownWarningItem>> getAllModuleDownWarningItems() {
        List<ModuleDownWarningItem> dataList = new ArrayList<ModuleDownWarningItem>();
        dataList.add(new ModuleDownWarningItem("A","H13","等待下模组","2016121010000001",360000L));
        dataList.add(new ModuleDownWarningItem("A","H14","等待下模组","2016121010000002",540000L));
        dataList.add(new ModuleDownWarningItem("A","H15","等待下模组","2016121010000003",680000L));
        dataList.add(new ModuleDownWarningItem("A","H16","等待下模组","2016121010000004",760000L));
        dataList.add(new ModuleDownWarningItem("A","H17","等待下模组","2016121010000005",260000L));
        dataList.add(new ModuleDownWarningItem("A","H18","等待下模组","2016121010000006",390000L));
        dataList.add(new ModuleDownWarningItem("A","H19","等待下模组","2016121010000007",500000L));
        dataList.add(new ModuleDownWarningItem("A","H20","等待下模组","2016121010000008",830000L));
        dataList.add(new ModuleDownWarningItem("A","H21","等待下模组","2016121010000009",550000L));
        for (int i=0;i<dataList.size();i++){
            dataList.get(i).setEndTime(System.currentTimeMillis()+dataList.get(i).getCountdown());
            dataList.get(i).setId(i);
        }
        return Observable.just(dataList);
        //return getService().getModuleDownWarningItems().compose(RxsRxSchedulers.<List<ModuleDownWarningItem>>io_main());
    }
}
