package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpWarningItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpPresenter extends BasePresenter<ModuleUpContract.Model,ModuleUpContract.View>{

    @Inject
    public ModuleUpPresenter(ModuleUpContract.Model model, ModuleUpContract.View mView) {
        super(model, mView);
    }

    public void getAllModuleUpWarningItems(){
        getModel().getAllModuleUpWarningItems().subscribe(new Action1<List<ModuleUpWarningItem>>() {
            @Override
            public void call(List<ModuleUpWarningItem> moduleUpWarningItems) {
                getView().onSuccess(moduleUpWarningItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }
}
