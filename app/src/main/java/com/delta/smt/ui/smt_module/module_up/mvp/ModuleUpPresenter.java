package com.delta.smt.ui.smt_module.module_up.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */

public class ModuleUpPresenter extends BasePresenter<ModuleUpContract.Model,ModuleUpContract.View>{

    @Inject
     ModuleUpPresenter(ModuleUpContract.Model model, ModuleUpContract.View mView) {
        super(model, mView);
    }

    public void getModuleUpWarningList(){
        getModel().getModuleUpWarningList().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<Result<ModuleUpWarningItem>>() {
            @Override
            public void call(Result<ModuleUpWarningItem> moduleUpWarningItemResult) {
                try{
                    if (0 == moduleUpWarningItemResult.getCode()) {

                        if (moduleUpWarningItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
                        }else {
                            getView().showContentView();
                            getView().onGetWarningListSuccess(moduleUpWarningItemResult.getRows());
                        }

                    } else {
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
