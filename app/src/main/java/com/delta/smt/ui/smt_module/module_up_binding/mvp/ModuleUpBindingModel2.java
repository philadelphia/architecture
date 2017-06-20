package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.Result;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingModel2 extends BaseModel<ApiService> implements ModuleUpBindingContract.Model2{

    public ModuleUpBindingModel2(ApiService apiService) {
        super(apiService);
    }


    //上传到MES
    @Override
    public Observable<Result> upLoadToMesManually(String value) {
        return getService().upLoadToMesManually(value).compose(RxsRxSchedulers.<Result>io_main());
    }

    //获取待上传到MES的列表
    @Override
    public Observable<Result> getAllItemsNeedTobeUpLoadToMES(String value){
       return getService().getAllItemsNeedTobeUpLoadToMES(value).compose(RxsRxSchedulers.<Result>io_main());
    }

}
