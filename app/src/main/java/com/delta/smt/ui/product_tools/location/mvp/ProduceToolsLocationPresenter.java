package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationPresenter extends BasePresenter<ProduceToolsLocationContract.Model,ProduceToolsLocationContract.View> {
    public ProduceToolsLocationPresenter(ProduceToolsLocationContract.Model model, ProduceToolsLocationContract.View mView) {
        super(model, mView);
    }

    public ProduceToolsLocationPresenter(ProduceToolsLocationContract.View rootView) {
        super(rootView);
    }
}
