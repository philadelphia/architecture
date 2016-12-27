package com.delta.smt.ui.production_warning.mvp.presenter;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.ui.production_warning.mvp.contract.ProduceWarningContract;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningPresenter extends BasePresenter<ProduceWarningContract.Model,ProduceWarningContract.View> {
    public ProduceWarningPresenter(ProduceWarningContract.Model model, ProduceWarningContract.View mView) {
        super(model, mView);
    }


}
