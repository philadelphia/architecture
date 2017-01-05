package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.VirtualLineBindingItem;

import java.util.List;

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

    public void getAllVirtualLineBindingItems(){
        getModel().getAllVirtualLineBindingItems().subscribe(new Action1<List<VirtualLineBindingItem>>() {
            @Override
            public void call(List<VirtualLineBindingItem> virtualLineBindingItems) {
                getView().onSuccess(virtualLineBindingItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

}
