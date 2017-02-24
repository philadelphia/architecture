package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpWarningItem;

import javax.inject.Inject;

import rx.functions.Action0;
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
        getModel().getAllModuleUpWarningItems().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<ModuleUpWarningItem>() {
            @Override
            public void call(ModuleUpWarningItem moduleUpWarningItems) {
                try{
                    if ("0".equals(moduleUpWarningItems.getCode())) {

                        if (moduleUpWarningItems.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(moduleUpWarningItems);
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpWarningItems);
                        }

                    } else {
                        getView().onFalied(moduleUpWarningItems);
                        getView().showErrorView();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().onNetFailed(throwable);
                    getView().showErrorView();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
