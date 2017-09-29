package com.delta.smt.ui.smt_module.module_down.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
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
        }).subscribe(new Action1<Result<ModuleDownWarningItem>>() {
            @Override
            public void call(Result<ModuleDownWarningItem> moduleDownWarningItemResult) {
                try{
                    if (moduleDownWarningItemResult.getCode() == 0) {

                        if (moduleDownWarningItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
//                            getView().onFailed(moduleDownWarningItemResult);
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleDownWarningItemResult.getRows());
                        }

                    } else {
                        getView().showErrorView();
                        getView().onFailed(moduleDownWarningItemResult.getMessage());
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
