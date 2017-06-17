package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import android.content.Context;
import android.widget.Toast;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingPresenter extends BasePresenter<ModuleUpBindingContract.Model,ModuleUpBindingContract.View> {
    @Inject
    public ModuleUpBindingPresenter(ModuleUpBindingContract.Model model, ModuleUpBindingContract.View mView) {
        super(model, mView);
    }

    public void getAllModuleUpBindingItems(String str){
        getModel().getAllModuleUpBindingItems(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).subscribe(new Action1<Result<ModuleUpBindingItem>>() {
            @Override
            public void call(Result<ModuleUpBindingItem> moduleUpBindingItemResult) {
                try{
                    if (0 == moduleUpBindingItemResult.getCode()) {

                        if (moduleUpBindingItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
//                            getView().onFailed(moduleUpBindingItems);
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpBindingItemResult.getRows());
                        }

                    } else {
                        getView().onFailed(moduleUpBindingItemResult.getMessage());
                        getView().showErrorView();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onNetFailed(throwable);
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void upLoadToMESManually(String value){
        getModel().upLoadToMesManually(value).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()){
                    getView().showMessage(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });

    }

    public void getMaterialAndFeederBindingResult(String str){
        getModel().getMaterialAndFeederBindingResult(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<Result<ModuleUpBindingItem>>() {
            @Override
            public void call(Result<ModuleUpBindingItem> moduleUpBindingItemResult) {
                try{
                    if (0 == moduleUpBindingItemResult.getCode()) {

                        if (moduleUpBindingItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
//                            getView().onFailedBinding(moduleUpBindingItem.getMessage());
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpBindingItemResult.getRows());
                        }

                    } else {
                        getView().onFailed(moduleUpBindingItemResult.getMessage());
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
