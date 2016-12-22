package com.delta.smt.ui.production_warning.mvp.presenter;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.production_warning.mvp.contact.ProductionLineContract;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:42
 */


public class ProductionLinePresenter extends BasePresenter<ProductionLineContract.Model, ProductionLineContract.View> {


    public ProductionLinePresenter(ProductionLineContract.Model model, ProductionLineContract.View mView) {
        super(model, mView);
    }
}
