package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.ProductWorkItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@ActivityScope
public class ProduceToolsBorrowPresenter extends BasePresenter<ProduceToolsBorrowContract.Model,ProduceToolsBorrowContract.View> {

    @Inject
    public ProduceToolsBorrowPresenter(ProduceToolsBorrowContract.Model model, ProduceToolsBorrowContract.View mView) {
        super(model, mView);
    }

    public void getData(){
        getModel().getProductWorkItem().subscribe(new Action1<List<ProductWorkItem>>() {
            @Override
            public void call(List<ProductWorkItem> productWorkItems) {
                getView().getFormData(productWorkItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFail();
            }
        });
    }
}
