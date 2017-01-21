package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownDetailsModel extends BaseModel<ApiService> implements ModuleDownDetailsContract.Model{
    public ModuleDownDetailsModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<ModuleDownDetailsItem> getAllModuleDownDetailsItems(String str) {
        /*List<ModuleDownDetailsItem> dataList = new ArrayList<ModuleDownDetailsItem>();
        dataList.add(new ModuleDownDetailsItem("KT8FL 139053","0351234700","-","03T021","Feeder缓存区","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139054","0351234701","-","03T022","Feeder缓存区","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139055","0351234702","-","03T023","Feeder缓存区","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139056","0351234703","-","03T024","Feeder维护区","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139057","0351234704","-","03T025","Feeder维护区","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139058","0351234705","-","03T026","尾数仓","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 139059","0351234706","-","03T027","尾数仓","2016082500"));
        dataList.add(new ModuleDownDetailsItem("KT8FL 1390510","0351234707","-","03T028","尾数仓","2016082500"));
        return Observable.just(dataList);*/
        return getService().getModuleDownDetailsItems(str).compose(RxsRxSchedulers.<ModuleDownDetailsItem>io_main());
    }

    @Override
    public Observable<ModuleDownMaintain> getModuleDownMaintainResult(String str) {
        return getService().getModuleDownMaintainResult(str).compose(RxsRxSchedulers.<ModuleDownMaintain>io_main());
    }
}
