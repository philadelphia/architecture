package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBorrowPresenter extends BasePresenter<ProduceToolsBorrowContract.Model,ProduceToolsBorrowContract.View> {
    public ProduceToolsBorrowPresenter(ProduceToolsBorrowContract.Model model, ProduceToolsBorrowContract.View mView) {
        super(model, mView);
    }

    public ProduceToolsBorrowPresenter(ProduceToolsBorrowContract.View rootView) {
        super(rootView);
    }
}
