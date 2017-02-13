package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModNumByMaterialResult;
import com.delta.smt.entity.VirtualBindingResult;
import com.delta.smt.entity.VirtualLineBindingItem;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingPresenter extends BasePresenter<VirtualLineBindingContract.Model,VirtualLineBindingContract.View>{
    @Inject
    public VirtualLineBindingPresenter(VirtualLineBindingContract.Model model, VirtualLineBindingContract.View mView) {
        super(model, mView);
    }

    public void getAllVirtualLineBindingItems(String str){
        getModel().getAllVirtualLineBindingItems(str).subscribe(new Action1<VirtualLineBindingItem>() {
            @Override
            public void call(VirtualLineBindingItem virtualLineBindingItems) {
                getView().onSuccess(virtualLineBindingItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

    public void getAllVirtualBindingResult(String id,String virtualID){
        getModel().getVirtualBinding(id,virtualID).subscribe(new Action1<VirtualBindingResult>() {
            @Override
            public void call(VirtualBindingResult virtualBindingResult) {

                getView().onSuccessBinding(virtualBindingResult);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailBinding();
            }
        });
    }

    public void getModNumByMaterial(String str,String num){
        getModel().getModNumByMaterial(str,num).subscribe(new Action1<ModNumByMaterialResult>() {
            @Override
            public void call(ModNumByMaterialResult modNumByMaterialResult) {

                getView().onSuccessGetModByMate(modNumByMaterialResult);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailGetModByMate();
            }
        });
    }

}
