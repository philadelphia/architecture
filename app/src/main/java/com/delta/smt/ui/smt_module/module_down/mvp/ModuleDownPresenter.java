package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownPresenter extends BasePresenter<ModuleDownContract.Model,ModuleDownContract.View>{

    @Inject
    public ModuleDownPresenter(ModuleDownContract.Model model, ModuleDownContract.View mView) {
        super(model, mView);
    }

    public void getAllModuleDownWarningItems(){
        getModel().getAllModuleDownWarningItems().subscribe(new Action1<List<ModuleDownWarningItem>>() {
            @Override
            public void call(List<ModuleDownWarningItem> moduleDownWarningItems) {
                getView().onSuccess(moduleDownWarningItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }
}