package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */
@ActivityScope
public class ProduceToolsBorrowPresenter extends BasePresenter<ProduceToolsBorrowContract.Model,ProduceToolsBorrowContract.View> {

    @Inject
    public ProduceToolsBorrowPresenter(ProduceToolsBorrowContract.Model model, ProduceToolsBorrowContract.View mView) {
        super(model, mView);
    }

}
