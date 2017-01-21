package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import android.content.Context;
import android.widget.Toast;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.MaterialAndFeederBindingResult;
import com.delta.smt.entity.ModuleUpBindingItem;

import javax.inject.Inject;

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
        getModel().getAllModuleUpBindingItems(str).subscribe(new Action1<ModuleUpBindingItem>() {
            @Override
            public void call(ModuleUpBindingItem moduleUpBindingItems) {
                getView().onSuccess(moduleUpBindingItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

    public void upLoadToMES(){
        Toast.makeText((Context) getView(),"上传到MES",Toast.LENGTH_SHORT).show();
    }

    public void getMaterialAndFeederBindingResult(String id,String feederID){
        getModel().getMaterialAndFeederBindingResult(id,feederID).subscribe(new Action1<MaterialAndFeederBindingResult>() {
            @Override
            public void call(MaterialAndFeederBindingResult materialAndFeederBindingResult) {
                getView().onSuccessBinding(materialAndFeederBindingResult);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailedBinding();
            }
        });
    }
}
