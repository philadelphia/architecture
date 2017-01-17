package com.delta.smt.ui.smt_module.module_up.mvp;

import android.content.Context;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.FeederSupplyItem;
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
    public Observable<List<ModuleUpWarningItem>> getAllModuleUpWarningItems() {
        List<ModuleUpWarningItem> dataList = new ArrayList<ModuleUpWarningItem>();
                /*dataList.add(new ModuleUpWarningItem("A","H13","01:00","仓库物料正在上模组","2016121010000001",3600L));
                falutMesage.setEndTime(System.currentTimeMillis()+falutMesage.getCountdown());
                falutMesage.setId(i);*/
        dataList.add(new ModuleUpWarningItem("A","H14","仓库物料正在上模组","2016121010000002","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H15","仓库物料正在上模组","2016121010000003","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H16","仓库物料正在上模组","2016121010000004","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H17","仓库物料正在上模组","2016121010000005","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H18","仓库物料正在上模组","2016121010000006","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H19","仓库物料正在上模组","2016121010000007","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H20","仓库物料正在上模组","2016121010000008","540000L"));
        dataList.add(new ModuleUpWarningItem("A","H21","仓库物料正在上模组","2016121010000009","540000L"));
        for(int i=0;i<dataList.size();i++){
            dataList.get(i).setEndTime(System.currentTimeMillis()+dataList.get(i).getCountDownLong());
            dataList.get(i).setId(i);
        }
        return Observable.just(dataList);
        //return getService().getModuleUpWarningItems().compose(RxsRxSchedulers.<List<ModuleUpWarningItem>>io_main());
    }
}
