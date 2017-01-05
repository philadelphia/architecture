package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsPresenter extends BasePresenter<ModuleDownDetailsContract.Model,ModuleDownDetailsContract.View> {
    @Inject
    public ModuleDownDetailsPresenter(ModuleDownDetailsContract.Model model, ModuleDownDetailsContract.View mView) {
        super(model, mView);
    }

    public void getAllModuleDownDetailsItems(){
        getModel().getAllModuleDownDetailsItems().subscribe(new Action1<List<ModuleDownDetailsItem>>() {
            @Override
            public void call(List<ModuleDownDetailsItem> moduleDownWarningItems) {
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
