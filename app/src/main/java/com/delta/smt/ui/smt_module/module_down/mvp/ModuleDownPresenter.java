package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleDownWarningItem;

import javax.inject.Inject;

import rx.functions.Action0;
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
        getModel().getAllModuleDownWarningItems().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).subscribe(new Action1<ModuleDownWarningItem>() {
            @Override
            public void call(ModuleDownWarningItem moduleDownWarningItems) {
                //getView().onSuccess(moduleDownWarningItems);
                try{
                    if ("0".equals(moduleDownWarningItems.getCode())) {

                        if (moduleDownWarningItems.getRows().size() == 0) {
                            getView().showEmptyView();
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleDownWarningItems);
                        }

                    } else {
                        getView().onFalied();
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
                    getView().onFalied();
                    getView().showErrorView();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
