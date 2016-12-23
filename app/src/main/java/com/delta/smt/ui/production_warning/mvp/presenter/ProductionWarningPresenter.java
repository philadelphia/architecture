package com.delta.smt.ui.production_warning.mvp.presenter;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.ui.production_warning.mvp.contact.ProductionWarningContract;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProductionWarningPresenter extends BasePresenter<ProductionWarningContract.Model,ProductionWarningContract.View> {
    public ProductionWarningPresenter(ProductionWarningContract.Model model, ProductionWarningContract.View mView) {
        super(model, mView);
    }
}
