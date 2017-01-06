package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackPresenter extends BasePresenter<ProduceToolsBackContract.Model,ProduceToolsBackContract.View> {
    public ProduceToolsBackPresenter(ProduceToolsBackContract.Model model, ProduceToolsBackContract.View mView) {
        super(model, mView);
    }

    public ProduceToolsBackPresenter(ProduceToolsBackContract.View rootView) {
        super(rootView);
    }
}
