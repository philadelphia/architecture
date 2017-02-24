package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsPresenter extends BasePresenter<ModuleDownDetailsContract.Model,ModuleDownDetailsContract.View> {
    @Inject
    public ModuleDownDetailsPresenter(ModuleDownDetailsContract.Model model, ModuleDownDetailsContract.View mView) {
        super(model, mView);
    }

    public void getAllModuleDownDetailsItems(String str){
        getModel().getAllModuleDownDetailsItems(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<ModuleDownDetailsItem>() {
            @Override
            public void call(ModuleDownDetailsItem moduleDownDetailsItem) {
                try{
                    if ("0".equals(moduleDownDetailsItem.getCode())) {
                        if (moduleDownDetailsItem.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFailed(moduleDownDetailsItem.getMsg());
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleDownDetailsItem);
                        }
                    } else {
                        getView().onFailed(moduleDownDetailsItem.getMsg());
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

    public void getAllModuleDownMaintainResult(String str){
        getModel().getModuleDownMaintainResult(str).subscribe(new Action1<ModuleDownMaintain>() {
            @Override
            public void call(ModuleDownMaintain moduleDownMaintain) {
                if ("0".equals(moduleDownMaintain.getCode())) {
                    getView().onSuccessMaintain(moduleDownMaintain);
                } else {
                    getView().onFailMaintain(moduleDownMaintain);
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onNetFailed(throwable);
            }
        });
    }

    public  void getDownModuleList(String condition){
        getModel().getDownModuleList(condition).subscribe(new Action1<ModuleDownDetailsItem>() {
            @Override
            public void call(ModuleDownDetailsItem moduleDownDetailsItem) {
                getView().onSuccess(moduleDownDetailsItem);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void getFeederCheckInTime(String condition){
        getModel().getFeederCheckInTime(condition).subscribe(new Action1<ModuleDownDetailsItem>() {
            @Override
            public void call(ModuleDownDetailsItem moduleDownDetailsItem) {
                    if (moduleDownDetailsItem.getCode().equalsIgnoreCase("0")){
                        getView().onSuccess(moduleDownDetailsItem);
                    }else {
                        getView().onFailed(moduleDownDetailsItem.getMsg());
                    }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onNetFailed(throwable);
            }
        });
    }
}
