package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import android.content.Context;
import android.widget.Toast;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpBindingItem;

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
        }).subscribe(new Action1<ModuleUpBindingItem>() {
            @Override
            public void call(ModuleUpBindingItem moduleUpBindingItems) {
                //getView().onSuccess(moduleUpBindingItems);
                try{
                    if ("0".equals(moduleUpBindingItems.getCode())) {

                        if (moduleUpBindingItems.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(moduleUpBindingItems);
                        }else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpBindingItems);
                        }

                    } else {
                        getView().onFalied(moduleUpBindingItems);
                        getView().showErrorView();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, new Action1<Throwable>() {
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

    public void upLoadToMES(){
        Toast.makeText((Context) getView(),"“上传到MES”功能待添加！",Toast.LENGTH_SHORT).show();
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
        }).subscribe(new Action1<ModuleUpBindingItem>() {
            @Override
            public void call(ModuleUpBindingItem moduleUpBindingItem) {
                try{
                    if ("0".equals(moduleUpBindingItem.getCode())) {

                        if (moduleUpBindingItem.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFailedBinding(moduleUpBindingItem);
                        }else {
                            getView().showContentView();
                            getView().onSuccessBinding(moduleUpBindingItem);
                        }

                    } else {
                        getView().onFailedBinding(moduleUpBindingItem);
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
